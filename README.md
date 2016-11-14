# Dudoser

[![Build Status](http://ci.rbkmoney.com/buildStatus/icon?job=rbkmoney_private/dudoser/master)](http://ci.rbkmoney.com/job/rbkmoney_private/job/dudoser/job/master/)

Сервис уведомляющий плательщика об успешном платеже


### Developers

- [Anatoly Cherkasov](https://github.com/avcherkasov)


### Оглавление:

1. [Полезные ссылки](docs/useful_links.md)
1. [FAQ](docs/faq.md)


---
[Журнал изменений](CHANGELOG.md)

### TODO

1. Сервис должен предоставлять интерфейс для отправки писем (смс, push-уведомлений, далее просто "письма"). 
  Возможны варианты отправки письма со следующими параметрами:
  а) тема и тело письма, вложения на указанный адрес
  б) данные мерчанта и/или инвойса, вложения, идентификатор шаблона письма на указанный адрес
  в) данные мерчанта и/или инвойса, вложения, messType+merchID+shopID на указанный адрес
2. Сервис должен предоставлять интерфейс для добавления/редактирования шаблонов писем.
  а) Добавить новый шаблон (для писем это, например, ftl-шаблон) с вложениями (если есть). 
     Может содержать в себе картинки в base64 формате прямо в шаблоне.
     Формат шаблона - строка, вложения - это список массивов байт.
  б) Все то же, что в а), плюс еще передается messType+merchID+shopID
  в) Редактировать/удалить существующий шаблон  
3. Шаблоны и вложения сохраняются в БД (Postgres)  
4. Шаблоны могут привязываться к связке messType+merchID+shopID (нужно для п. 1в) 
5. Если шаблон не найден в 1б, генериуется исключение. 
   Если не найден в 1в, то ищется сначала по messType+merchID. 
   Если не найден и по этой связке, то ищется по messType.
6. Существующий механизм поллинга в сервисе так же должен использовать шаблоны.

**К бете необходимо реалиовать п.1.**
**Остальные пункты (2-6) будут реализованы после беты. Пока их необходимо только предусмотреть в протоколе.**