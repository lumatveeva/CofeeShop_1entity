# CoffeeShop CRM

## О проекте

Предполагается, что у нас есть кофейня и она принимает заказы, используя CRM систему. Попадая в систему, заказ может пройти следующие стадии:

- **Заказ зарегистрирован**
- **Заказ принят в работу**
- **Заказ отменен**
- **Заказ готов к выдаче**
- **Заказ выдан**

## Требования

- Любому из событий должно предшествовать событие регистрации заказа.
- Если заказ уже выдан или отменен, то публикация новых событий недоступна.

## Технологии и инструменты

- **Spring Boot** — основной фреймворк для разработки приложения.
- **Liquibase** — инструмент для версионирования базы данных.
- **Swagger** — инструмент для документирования и тестирования API.
- **Docker и Docker Compose** — инструменты для контейнеризации.
- **JUnit** — фреймворк для модульного тестирования.

## Описание решения

Приложение представляет собой систему для работы с сущностью событие.  
Сущность заказ собирается с помощью агрегации событий, которые происходили с данным заказом.

- База данных - Postgresql.  
  Добавлен Liquibase для автоматической генерации нужных таблиц.
- Для возможности быстро начать работать с сущностями добавлен класс рандомной генерации сущности заказ.
- Для более простой работы с запросами добавлен Swagger (без примеров успешных запросов), доступный по адресу /api-doc/swagger/swagger-ui/index.html.
- Для развертывания приложения создан файл docker-compose.yml.
- Так как приложение представляет собой решение небольшой задачи, покрытие тестами минимальное, без поднятия в них базы данных.
