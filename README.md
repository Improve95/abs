### Запуск

#### Авторизация

1) настройте почту с которой будут присылаться сообщения
````
замените в application.yml настройки mail.username и mail.password
на адрес и пароль приложения от почты с который хотите получать сообщения
mail:
 username: testmail@gmail.com
 password: rsdihkjnxds
````
2) настройте секретный ключ jwt токена
````
замените в application.yml настройку app.token.secret на любую строку
````
3) docker-compose up -d

4) все эндпоинты доступны по адресу: http://localhost:8074/swagger-ui/index.html

5) админом можно стать через изменения бд, админ имеет только ему доступные методы в контроллере

7) есть восстановление пароля через почту, (подтверждения почты нет ¯\\__(ツ)_/¯)

8)  проверяющим t1 будут интересны:
````
ru.improve.abs.service.api.controller.AdminController
ru.improve.abs.service.api.controller.AuthController
и все пакеты:
ru.improve.abs.service.configuration.security
ru.improve.abs.service.core.security
````
ну и можете посмотреть чего я тут вообще наделал, если понравтся авторизация :DDD

#### graphQL

1) вся документация методов доступны по эндпоинту http://localhost:8074/graphql в postman,
для их отправки необходима авторизация

