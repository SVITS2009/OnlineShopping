Online-Shopping Spring Boot Application

H2 DB (In memory Database) + Spring Boot + JPA + Apache Kafka  + Redis cache + Git

Software required for local setup( Without container) -
* install JDK 11
* Maven 3
* Git
* Java IDE (IntelliJ)
* Apache Kafka (kafka_2.13-3.3.2)
* Docker Desktop ( If we want to run the service inside container)
* Running local redis container 

* Clone the project - git clone https://github.com/SVITS2009/online-shoppinig.git

* Go to project directory and run the below commands.
  - Clean - mvn clean
  - Run the test cases - mvn test
  - Run install - mvn clean package

Instruction before running the service -
* Kafka Setup 
  - download and extract the kafka in any directory. 
  - open 3 command prompts and go to kafka directory for all. 
  - Run the zookeeper using the below command - 
  .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 
  - Run the Kafka server using the below command -
  .\bin\windows\kafka-server-start.bat .\config\server.properties 
  - Run the Kafka console consumer to see the Item related data in to item_topic using the below command -
  ./bin/kafka-console-consumer.sh --topic item_topic --from-beginning --bootstrap-server localhost:9092 

* Redis cache setup
  - Run redis cache as a docker container using the below commands
    - docker pull redis
    - docker run -d --name redis-cache -p 6379:6379 redis

* Running Application using the below options.
  There are several ways to run a Spring Boot application on your local machine. One way is to
  
  1.) Run the main method in the com.talan.onlineshopping.OnlineShoppingApplication class from your IDE.
  
  2.) Alternatively you can use the Spring Boot Maven plugin like so:
  
      mvn spring-boot:run

If we want to run the service in the container. Please follow the same steps for Kafka setup and Redis cache setup.
* Go to project directory and run the below commands.
  - Clean - mvn clean
  - Run the test cases - mvn test
  - Run install - mvn clean package
  - Build the docker image using the dockerfile - docker build -t my-online-shopping-app .
  
* Once image build is completed. Now we will run the image inside the container.
  - docker run -p 8081:8081 my-online-shopping-app

* online-shopping service is running on 8081 port. Once application is running. 
  Please use the below end points to get the swagger document info. These end points are not required username and password.
  - To access swagger UI - http://localhost:8081/onlineShopping/swagger-ui/ 
  - To access swagger docs in Json form - http://localhost:8081/onlineShopping/v2/api-docs
  - To access H2 DB console - http://localhost:8081/onlineShopping/h2-console

We have online Shopping items related APIs like create, update , get Item, delete item and search item.
These end points are secured using the form-based login mechanism. If we want to access them. we need to enter the username/password.

- User information -
  Currently Application support ADMIN user.
    * ADMIN - admin/admin123

- Authentication mechanism
    * Form based Login
  
    




