# WoW Classic Hardcore Adventure Blog

With the release of WoW Classic Hardcore servers, five adventurers will team up to reach level 60 safely.
They will be broadcasting their adventure on a joint stream, and each one of them will be posting daily records in this blog.
Enjoy the adventure!

## Technical Information

This is a **Spring Boot 3.1** Microservice that builds with **Maven** and runs on **JDK 17**.

### Noteworthy Technologies and Libraries

- **H2 Database** - In memory database.
- **Lombok** - Helps reducing boilerplate code.

### Starting the Microservice

This Microservice runs an embedded **Apache Tomcat** server on **port 8080**.
Make sure the aforementioned port is available before starting this Microservice.

There are two ways of starting this Microservice, both via **Maven**.

#### Generate artifact with Maven and run it with JDK

Generate a .jar artifact using following Maven command within project folder:

> mvn clean install

This will generate a .jar artifact on target folder. Now run the artifact using the following Java command:

> java -jar [JAR_FILE_PATH]

#### Run directly through Spring Boot Maven script:

Or simply run the Microservice by running Spring Boot Maven script using the following Maven command within project folder:

> mvn spring-boot:run

### Accessing the Microservice

After successfully starting the Microservice, you should be able to access its resources.
You can do it by performing HTTP requests using a tool like Postman / Insomnia, or a command like CURL.
Below there are some examples of the resources you can use:

Find all Authors:

> GET http://localhost:8080/hardcoreadventureblog/author

Find an Author:

> GET http://localhost:8080/hardcoreadventureblog/author/1

Create an Author:

> POST http://localhost:8080/hardcoreadventureblog/author

    {
        "name": "Test",
        "email": "test@test.com"
    }

Update an Author:

> PUT http://localhost:8080/hardcoreadventureblog/author/1

    {
        "name": "Test Changed",
        "email": "test_changed@test.com"
    }

Delete an Author:

> DELETE http://localhost:8080/hardcoreadventureblog/author/1

Find all Posts:

> GET http://localhost:8080/hardcoreadventureblog/post

Find a Post:

> GET http://localhost:8080/hardcoreadventureblog/post/1

Create a Post:

> POST http://localhost:8080/hardcoreadventureblog/post

    {
        "title": "First Post",
        "content": "Content of first post.",
        "postedOn": "2023-08-20T15:01:02.000000000Z",
        "authorId": 1
    }

Update a Post:

> PUT http://localhost:8080/hardcoreadventureblog/post/1

    {
        "title": "Title changed",
        "content": "Content changed"
    }

Delete a Post:

> DELETE http://localhost:8080/hardcoreadventureblog/post/1

#### H2 Console

You can access H2 console by accessing the following URL through your browser:

> http://localhost:8080/hardcoreadventureblog/h2-console

Find the correct JDBC URL in logs.