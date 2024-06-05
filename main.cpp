#include "qjsondocument.h"
#include "qjsonobject.h"
#include <QtCore/QCoreApplication>
#include <QtHttpServer/QHttpServer>
#include <QtSql>
#include <QSqlDatabase>
#include <QNetworkRequest>
#include <QNetworkAccessManager>
#include <QNetworkReply>

#define SCHEME "http"
#define HOST "127.0.0.1"
#define PORT 49425

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    QCoreApplication app(argc, argv);

    QSqlDatabase db;
    db = QSqlDatabase::addDatabase("QMYSQL");
    db.setHostName("nyacawa.mysql.database.azure.com");
    db.setUserName("kirixo");
    db.setPassword("ClowShenRa3000NyaOniKo");
    db.setDatabaseName("nyacawa_db");
    if(db.open()){
        qDebug() << "!!!!NyaCawa bd opened from main.cpp";
    } else {
        qDebug() << "db error";
    }
    QHttpServer server;

    server.route("/api/animelist", [] (const QHttpServerRequest &request) {
        qDebug() << "Request have been got";
        QJsonArray rows;
        QString queryString = "SELECT *, UNIX_TIMESTAMP(aired_start), UNIX_TIMESTAMP(aired_end) FROM titles";
        QSqlQuery query;
        query.prepare(queryString);
        if(query.exec()){
            for (int i = 0; query.next(); ++i) {
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
