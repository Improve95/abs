#### Запуск
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
6) до дефолту есть пользователь e.davydov@g.nsu.ru c паролем password1