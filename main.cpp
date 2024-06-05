#include "qjsondocument.h"
#include "qjsonobject.h"
#include <QtCore/QCoreApplication>
#include <QtHttpServer/QHttpServer>
#include <QtSql>
#include <QSqlDatabase>
#include <QNetworkRequest>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QCryptographicHash>
#include "types.h"
#include "utils.h"

struct SessionEntry : public Jsonable
{
    qint64 id;
    QString email;
    QString password;
    std::optional<QUuid> token;

    explicit SessionEntry(const QString &email, const QString &password)
        : id(nextId()), email(email), password(password)
    {
    }

    void startSession() { token = generateToken(); }

    void endSession() { token = std::nullopt; }

    QJsonObject toJson() const override
    {
        return token
                   ? QJsonObject{ { "id", id },
                                 { "token", token->toString(QUuid::StringFormat::WithoutBraces) } }
                   : QJsonObject{};
    }

    bool operator==(const QString &otherToken) const
    {
        return token && *token == QUuid::fromString(otherToken);
    }

private:
    QUuid generateToken() { return QUuid::createUuid(); }

    static qint64 nextId()
    {
        static qint64 lastId = 1;
        return lastId++;
    }
};

struct SessionEntryFactory : public FromJsonFactory<SessionEntry>
{
    std::optional<SessionEntry> fromJson(const QJsonObject &json) const override
    {
        if (!json.contains("email") || !json.contains("password"))
            return std::nullopt;

        return SessionEntry(json.value("email").toString(), json.value("password").toString());
    }
};

class SessionApi
{
public:
    explicit SessionApi(const IdMap<SessionEntry> &sessions,
                        std::unique_ptr<FromJsonFactory<SessionEntry>> factory)
        : sessions(sessions), factory(std::move(factory))
    {
    }

    QHttpServerResponse registerSession(const QHttpServerRequest &request)
    {
        const auto json = byteArrayToJsonObject(request.body());
        if (!json)
            return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);
        const auto item = factory->fromJson(*json);
        if (!item)
            return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);

        const auto session = sessions.insert(item->id, *item);
        session->startSession();
        return QHttpServerResponse(session->toJson());
    }

    QHttpServerResponse login(const QHttpServerRequest &request)
    {
        const auto json = byteArrayToJsonObject(request.body());

        if (!json || !json->contains("email") || !json->contains("password"))
            return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);

        auto maybeSession = std::find_if(
            sessions.begin(), sessions.end(),
            [email = json->value("email").toString(),
             password = json->value("password").toString()](const auto &it) {
                return it.password == password && it.email == email;
            });
        if (maybeSession == sessions.end()) {
            return QHttpServerResponse(QHttpServerResponder::StatusCode::NotFound);
        }
        maybeSession->startSession();
        return QHttpServerResponse(maybeSession->toJson());
    }

    QHttpServerResponse logout(const QHttpServerRequest &request)
    {
        const auto maybeToken = getTokenFromRequest(request);
        if (!maybeToken)
            return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);

        auto maybeSession = std::find(sessions.begin(), sessions.end(), *maybeToken);
        if (maybeSession != sessions.end())
            maybeSession->endSession();
        return QHttpServerResponse(QHttpServerResponder::StatusCode::Ok);
    }

    bool authorize(const QHttpServerRequest &request) const
    {
        const auto maybeToken = getTokenFromRequest(request);
        if (maybeToken) {
            const auto maybeSession = std::find(sessions.begin(), sessions.end(), *maybeToken);
            return maybeSession != sessions.end() && *maybeSession == *maybeToken;
        }
        return false;
    }

private:
    IdMap<SessionEntry> sessions;
    std::unique_ptr<FromJsonFactory<SessionEntry>> factory;
};

#define SCHEME "http"
#define HOST "127.0.0.1"
#define PORT 49427

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    QCoreApplication app(argc, argv);

    QSqlDatabase db;
    db = QSqlDatabase::addDatabase("QMYSQL");
    // db.setHostName("nyacawa.mysql.database.azure.com");
    // db.setUserName("kirixo");
    // db.setPassword("ClowShenRa3000NyaOniKo");
    db.setHostName("127.0.0.1");
    db.setPort(3307);
    db.setUserName("admin");
    db.setPassword("1111");
    db.setDatabaseName("nyacawa_db");
    if(db.open()){
        qDebug() << "!!!!NyaCawa bd opened from main.cpp";
    } else {
        qDebug() << "db error";
    }
    QHttpServer server;

    server.route("/api/animelist", [] (const QHttpServerRequest &request) {
        Q_UNUSED(request);
        QJsonArray rows;
        QString queryString = "SELECT *, UNIX_TIMESTAMP(aired_start), UNIX_TIMESTAMP(aired_end) FROM titles";
        QSqlQuery query;
        query.prepare(queryString);
        if(query.exec()){
            for (; query.next();) {
                QJsonObject row;
                row["id"] = query.value(0).toInt();
                row["name"] = query.value(1).toString();
                row["description"] = query.value(2).toString();
                row["image"] = query.value(3).toString();
                row["aired_start"] = query.value(8).toInt();
                row["aired_end"] = query.value(9).toInt();
                row["general_score"] = query.value(6).toDouble();
                row["total_episodes"] = query.value(7).toInt();
                rows.append(row);
            }
        } else {
            qDebug() << "Error executing query:" << query.lastError().text();
        }
        QJsonObject response;
        response["listOfAnime"] = rows;
        return QHttpServerResponse(QJsonDocument(response).toJson());
    });


// TODO: something was fixed, but header still needs for for fixes
    server.route("/api/animeinfo", [] (const QHttpServerRequest &request) {
        QJsonObject response;

        // Parse the query parameters
        QUrlQuery queryParams(request.url().query());

        // Check if 'id' parameter is present
        if (queryParams.hasQueryItem("id")) {
            QString id = queryParams.queryItemValue("id");
            QString queryString = "SELECT *, UNIX_TIMESTAMP(aired_start), UNIX_TIMESTAMP(aired_end) FROM titles WHERE id = :id";
            QSqlQuery query;
            query.prepare(queryString);
            query.bindValue(":id", id);

            if (query.exec()) {
                if (query.next()) {
                    QJsonObject row;
                    row["id"] = query.value(0).toInt();
                    row["name"] = query.value(1).toString();
                    row["description"] = query.value(2).toString();
                    row["image"] = query.value(3).toString();
                    row["aired_start"] = query.value(8).toInt();
                    row["aired_end"] = query.value(9).toInt();
                    row["general_score"] = query.value(6).toDouble();
                    row["total_episodes"] = query.value(7).toInt();
                    response["info"] = row;
                } else {
                    qDebug() << "No record found with id:" << id;
                }
            } else {
                qDebug() << "Error executing query:" << query.lastError().text();
            }
        } else {
            qDebug() << "Missing 'id' parameter in request";
        }
        return QHttpServerResponse(QJsonDocument(response).toJson());
    });


    server.route(
        "/api/login",
        [](const QHttpServerRequest &request) {

            qDebug() << "Login request";

            const auto json = byteArrayToJsonObject(request.body());

            if (!json || !json->contains("email") || !json->contains("password"))
                return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);

            QString email = json->value("email").toString();
            QString password = json->value("password").toString();

            QByteArray hashedPassword = QCryptographicHash::hash(password.toUtf8(), QCryptographicHash::Md5);

            QString hashedPasswordHex = QString(hashedPassword.toHex());

            QString queryString = "SELECT user_id, token FROM users WHERE email = :email AND password = :password";
            QSqlQuery query;
            query.prepare(queryString);

            query.bindValue(":email", email);
            query.bindValue(":password", hashedPasswordHex);


            QJsonObject response;
            if(query.exec()){
                for (; query.next();) {
                    QJsonObject row;
                    row["id"] = query.value(0).toInt();
                    row["token"] = query.value(1).toString();
                    response["loginData"] = row;

                }
            } else {
                qDebug() << "Error executing query:" << query.lastError().text();
            }

            qDebug() << query.lastQuery();

            return QHttpServerResponse(QJsonDocument(response).toJson());
        });

    server.route(
        "/api/reqister",
        [](const QHttpServerRequest &request) {

            qDebug() << "Register request";

            const auto json = byteArrayToJsonObject(request.body());

            if (!json || !json->contains("email") || !json->contains("name") || !json->contains("password"))
                return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);

            QString email = json->value("email").toString();
            QString name = json->value("name").toString();
            QString password = json->value("password").toString();
            QUuid token = QUuid::createUuid();

            QByteArray hashedPassword = QCryptographicHash::hash(password.toUtf8(), QCryptographicHash::Md5);

            // Converting the hashed password to a hexadecimal representation
            QString hashedPasswordHex = QString(hashedPassword.toHex());

            QJsonObject response;

            QSqlQuery userExists;
            userExists.prepare("SELECT user_id FROM users WHERE email = :email OR name = :name");
            userExists.bindValue(":name", name);
            userExists.bindValue(":email", email);

            if(userExists.exec()) {
                if(userExists.next()) {
                    response["message"] = 1; // email or nickname already exists
                    return QHttpServerResponse(response);
                }
            } else {
                return QHttpServerResponse(QHttpServerResponder::StatusCode::InternalServerError);
            }

            QString queryString = "INSERT INTO users (user_id, name, email, password, status, token)  VALUES "
                                  "(NULL, :name, :email, :password, :status, :token)";
            QSqlQuery query;

            query.prepare(queryString);
            query.bindValue(":name", name);
            query.bindValue(":email", email);
            query.bindValue(":password", hashedPasswordHex);
            query.bindValue(":status", "Кадет");
            query.bindValue(":token", token.toString(QUuid::StringFormat::WithoutBraces));

            if(query.exec()) {
                response["message"] = 0; // OK
            } else {
                qDebug() << "Error executing query:" << query.lastError().text();
            }

            qDebug() << query.lastQuery();

            return QHttpServerResponse(QJsonDocument(response).toJson());
        });


    server.route("/api/profileinfo", [] (const QHttpServerRequest &request) {
        QJsonObject response;

        // Parse the query parameters
        QUrlQuery queryParams(request.url().query());

        // Check if 'id' parameter is present
        if (queryParams.hasQueryItem("id")) {
            QString id = queryParams.queryItemValue("id");
            QString queryString = "SELECT name, status, bonus_points FROM users WHERE user_id = :id";
            QSqlQuery query;
            query.prepare(queryString);
            query.bindValue(":id", id);

            if (query.exec()) {
                if (query.next()) {
                    QJsonObject row;
                    row["name"] = query.value(0).toString();
                    row["status"] = query.value(1).toString();
                    row["bonus_points"] = query.value(2).toInt();
                    response["info"] = row;
                } else {
                    qDebug() << "No record found with id:" << id;
                }
            } else {
                qDebug() << "Error executing query:" << query.lastError().text();
            }
        } else {
            qDebug() << "Missing 'id' parameter in request";
        }
        return QHttpServerResponse(QJsonDocument(response).toJson());
    });

    server.route("/api/favorites", [] (const QHttpServerRequest &request) {
        QJsonObject response;

        QUrlQuery queryParams(request.url().query());

        if (!queryParams.hasQueryItem("id")) {
            qDebug() << "Missing 'id' parameter in request";
            return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);
        }

        QString id = queryParams.queryItemValue("id");

        QString queryString = "SELECT products.product_id, products.name, products.description, products.price, products.image "
                              "FROM products "
                              "JOIN wishlist ON products.product_id = wishlist.product_id "
                              "WHERE wishlist.user_id = :id";
        QSqlQuery query;
        query.prepare(queryString);
        query.bindValue(":id", id);

        if(!query.exec()) {
            qDebug() << "Error executing query:" << query.lastError().text();
            return QHttpServerResponse(QHttpServerResponder::StatusCode::InternalServerError);
        }

        QJsonArray rows;
        for (; query.next();) {
            QJsonObject row;
            row["id"] = query.value(0).toInt();
            row["name"] = query.value(1).toString();
            row["description"] = query.value(2).toString();
            row["price"] = query.value(3).toDouble();
            row["image"] = query.value(4).toString();
            //row["category_id"] = query.value(5).toInt();
            rows.append(row);
        }
        response["listOfFavorites"] = rows;

        qDebug() << rows.count() << " favorite goods have been returned.";
        return QHttpServerResponse(QJsonDocument(response).toJson());
    });


    const QHostAddress host = QHostAddress::Any;
    const quint16 port = PORT;

    if (!server.listen(host, port)) {
        qCritical() << "Could not start server";
        return 1;
    }

    qDebug() << "Server is listening on" << host.toString() << ":" << port;

    return app.exec();
    // httpServer.route(
    //     QString("%1").arg(apiPath), QHttpServerRequest::Method::Get,
    //     [&api](const QHttpServerRequest &request) { return api.getPaginatedList(request); });

    return a.exec();
}
