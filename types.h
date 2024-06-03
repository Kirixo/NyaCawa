#ifndef TYPES_H
#define TYPES_H
#include <QtGui/QColor>
#include <QtCore/QDateTime>
#include <QtCore/QJsonArray>
#include <QtCore/QJsonObject>
#include <QtCore/QJsonParseError>
#include <QtCore/QString>
#include <QtCore/qtypes.h>

#include <algorithm>
#include <optional>

struct Jsonable
{
    virtual QJsonObject toJson() const = 0;
    virtual ~Jsonable() = default;
};

struct Updatable
{
    virtual bool update(const QJsonObject &json) = 0;
    virtual void updateFields(const QJsonObject &json) = 0;
    virtual ~Updatable() = default;
};

template<typename T>
struct FromJsonFactory
{
    virtual std::optional<T> fromJson(const QJsonObject &json) const = 0;
    virtual ~FromJsonFactory() = default;
};


template<typename T>
class IdMap : public QMap<qint64, T>
{
public:
    IdMap() = default;
    explicit IdMap(const FromJsonFactory<T> &factory, const QJsonArray &array) : QMap<qint64, T>()
    {
        for (const auto &jsonValue : array) {
            if (jsonValue.isObject()) {
                const auto maybeT = factory.fromJson(jsonValue.toObject());
                if (maybeT) {
                    QMap<qint64, T>::insert(maybeT->id, *maybeT);
                }
            }
        }
    }
};

#endif // TYPES_H
