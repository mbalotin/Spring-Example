Spring Example 1.0
======

This is an example project using spring. 
It should be completely automatic with the exception of the mysql database creation.
Default database name is "example". It can be change in application.properties. 

```
spring.datasource.url = jdbc:mysql://localhost:3306/example
spring.datasource.username = root
spring.datasource.password =
```

MailerService is disabled. Enable it only after completing the necessary info in application.properties. 

More examples and explanations are in comments.

####WARNING: KEYSTORE IS INVALID. PLEASE CREATE ONE TO SUBSTITUTE.

--------------------------------------------------------------------

Supported operations
--------------------------------------------

####Adding publishers (admin only)

--------------------------------------------------------------------

- *Endpoint:* `POST http://localhost/publishers/new`
- *Authorization:* `BASIC bXVscHA6eGhDdGNxNW9U`
- *Accept-type:* `application/json`
- *Content-type:* `application/json`

```json
{
  "username": "name",
  "email": "email",
  "password": "password"
}
```

This example can also be seen by making a request to: `GET http://localhost/publishers`
You can also list the publishers by using: `GET http://localhost/publishers/list`

--------------------------------------------------------------------

####Adding scripts

--------------------------------------------------------------------

- *Endpoint:* `POST http://localhost/scripts/new`
- *Authorization:* `BASIC bXVscHA6eGhDdGNxNW9U`
- *Accept-type:* `application/json`
- *Content-type:* `application/json`

```json
{
  "name": "name",
  "content": "content - no linebreaks"
}
```

This example can also be seen by making a request to: `GET http://localhost/scripts`
You can also list your scripts by using: `GET http://localhost/scripts/list`

--------------------------------------------------------------------
