# URL - SHORTNER

This API is used to shorten a long URL and also to redirect shortened URL to the original long URL

## Installation

Docker can be used to run the application:

docker pull mysql (to pull mysql image)

docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=shorturl -e MYSQL_USER=sa -e MYSQL_PASSWORD=password  -d mysql:latest (to run mysql container)

the IP of mysql docker container should be updated in application.properties and then mvn clean install should be run to create a jar.

docker build  -t url-shortner . (to build url-shortner container from url-shortner.jar. should be run from the path where url-shortner.jar exists)

docker run -p 8080:8080 --name url-shortner --link mysql-standalone:mysql -d url-shortner (to run url-shortner container. Make sure 8080 port is not being used by any other application)


## Usage

Creating short URL:

POST request should be made to URL : http://<hostname>:8080/createUrl

Headers :

content-type : application/json

body : {"longUrl":"<any valid long url>"}

Calling shortURL:

GET request should be made to URL : http://<hostname>:8080/<shortURL>

** To know the health and basic statics of the application : http://<hostname>:8080/actuator**
## Credits

Author: Charu Agarwal