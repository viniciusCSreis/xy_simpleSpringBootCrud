# xy_simpleSpringBootCrud
simple crud in spring boot 

# How Use

 - Start Postgres Docker
 
 ```
 docker-compose up -d
 ```

> **Note:**  the docker-compose start a postgres and a pgadmin4 you can access the pgadmin4 on the port 3001 and you can set a user and a password to the pgadmin4 on the docker-compose.yml at PGADMIN_DEFAULT_EMAIL and PGADMIN_DEFAULT_PASSWORD

- Start Spring 


```
mvn clean install
mvn spring-boot:run
```
> **Note:** the spring-boot start at port 8080 and you can access from your browser http://localhost:8080/ 
