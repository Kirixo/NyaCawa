#include "qjsondocument.h"
#include "qjsonobject.h"
#include <QtCore/QCoreApplication>
#include <QtHttpServer/QHttpServer>

#define SCHEME "http"
#define HOST "127.0.0.1"
#define PORT 49425

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    QCoreApplication app(argc, argv);

    QHttpServer server;

    server.route("/api/data", [] (const QHttpServerRequest &request) {
        QJsonObject response;
        response["message"] = "Responce from Qt! 1234";
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
