# rss-aggregator
News parser and aggregator.

Примеры адресов и правил парсинга, которые можно подать ему на вход:

## Парсинг ленты RSS
Парсинг ленты универсален, можно парсить разные ленты без изменения правила.
```
curl --location --request POST 'http://localhost:8080/address' \
--header 'Content-Type: application/json' \
--data-raw '{
    "address": "https://news.yandex.ru/cyber_sport.rss",
    "rule": {
        "type" : "RSS",
        "item": "",
        "title": "",
        "link": "",
        "pubDate": "",
        "pubDateFormat": "",
        "img" : "",
        "description": ""
    }
}'
```
## Парсинг новостного сайта
Парсинг сайта привязан к правилу в котором указаны html теги элементов или правила по которым нужно парсить.
###### Особенности:
- link - указывается тег элемента или значение inside при котором ссылка будет искаться внутри элемента title.
- pubDateFormat - указывается формат даты указаный на сайте в формате SimpleDateFormat. Подробнее [тут.](http://tutorials.jenkov.com/java-internationalization/simpledateformat.html)
```
curl --location --request POST 'http://localhost:8080/address' \
--header 'Content-Type: application/json' \
--data-raw '{
    "address": "https://www.newsru.com",
    "rule": {
        "type" : "WEBSITE",
        "item": "left-feed-item",
        "title": "left-feed-title",
        "link": "inside",
        "pubDate": "",
        "pubDateFormat": "dd-MM-yy",
        "img" : "left-feed-img",
        "description": "left-feed-anons"
    }
}'
```

