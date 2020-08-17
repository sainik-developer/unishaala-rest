# unishaala-REST-api
### AWS credentials global shaala
```text
* info@globalshaala.com
* Freebies1!
```
### Access POSTGRES RDS local and AWS
##### Local Access to RDS localhost 
```text
$ sudo -u postgres psql postgres
$ \c unishaal
$ \dt // list all tables
$ \d <table-name> // for table description
$ SELECT * FROM <table_name>
```
##### Access to Postgres on AWS RDS
```groovy
$ psql -h shaala-db.cjngusrmwizh.ap-south-1.rds.amazonaws.com -p 5432 -U postgres -W postgres
```
#### How to install AWS CLI 
```html
$ curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
$ unzip awscliv2.zip
$ sudo ./aws/install

$ aws configure
AWS Access Key ID [None]: AKIASFUGFQJDPYYGCAS7
AWS Secret Access Key [None]: 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
Default region name [None]: ap-south-1
Default output format [None]: json
```
##### Retrieve an authentication token and authenticate your Docker client to your registry.
```groovy
$ aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 149531492934.dkr.ecr.ap-south-1.amazonaws.com
$ unzip awscliv2.zip
$ sudo ./aws/install
```
#### Build app and make docker and push to AWS ECR
```html
$ ./gradlew clean bootJar
$ docker build -t spring-shaala:<version> .
$ docker tag shaala-back-spring:<version> 149531492934.dkr.ecr.ap-south-1.amazonaws.com/shaala-back-spring:latest
$ docker push 149531492934.dkr.ecr.ap-south-1.amazonaws.com/shaala-back-spring:latest
```
##### Check the docker logs
1. Connect to ec2                           `$ ssh -i "shaala-ec2.pem" ec2-user@ec2-15-206-160-16.ap-south-1.compute.amazonaws.com`
2. list all running or stopped container    `$ docker ps -a`
3. tail the docker running container logs   `$ docker logs --follow 116b9623f4bb`
4. Remove all the container not in use      `$ docker system prune`

#### Google cloud domain admin credentials
```html
info@globalshaala.com
globalively
```
#### Liquibase 
https://www.baeldung.com/liquibase-refactor-schema-of-java-app#hibernate
Dependency to use liquibase
```text
<dependency>
    <groupId>org.liquibase</groupId>
     <artifactId>liquibase-core</artifactId>
      <version>3.4.1</version>
</dependency>
```
Plugin to generate changelog using maven command `mvn liquibase:generateChangeLog`
```text
<plugins>
    <plugin>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-maven-plugin</artifactId>
        <version>3.4.1</version>
        <configuration>                  
            <propertyFile>src/main/resources/liquibase.properties</propertyFile>
        </configuration> 
        <dependencies>
            <dependency>
                <groupId>org.liquibase.ext</groupId>
                <artifactId>liquibase-hibernate4</artifactId>
                <version>3.5</version>
            </dependency>
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-beans</artifactId>
                <version>4.1.7.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.data</groupId>
                <artifactId>spring-data-jpa</artifactId>
                <version>1.7.3.RELEASE</version>
            </dependency>
        </dependencies>               
    </plugin> 
</plugins>
```
`liquibase.properties`
```html 
url=jdbc:mysql://localhost:3306/oauth_reddit
username=tutorialuser
password=tutorialmy5ql
driver=com.mysql.jdbc.Driver
outputChangeLogFile=src/main/resources/liquibase-outputChangeLog.xml
```

### Dev Details for unix based system

#### Redis debian command on 
1. Status of Redis : `sudo systemctl status redis`
2. Stop redis as service `sudo systemctl stop redis`
3. Start redis as service : `sudo systemctl start redis`
4. Restart redis as service : `sudo systemctl restart redis`
#### Postgres debian command 
1. Status (running or stopped) : `sudo systemctl status postgresql`
2. Start : `sudo systemctl start postgresql`
3. Stop : `sudo systemctl stop postgresql`
4. Restart : `sudo systemctl restart postgresql`


#### How to run the app using mvn
```text
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
```text
-Dspring.profiles.active=dev
```

#### How to access the openapi/swagger documentation
```text
http://localhost:8080/swagger-ui.html
```
