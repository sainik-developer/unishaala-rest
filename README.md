# unishaala-REST-api
### AWS credentials global shaala
```html
* info@globalshaala.com
* Freebies1!
```
### Access POSTGRES RDS local and AWS
##### Local Access to RDS localhost 
```groovy
$ sudo -u postgres psql postgres
$ \c shaala
$ \dt
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
