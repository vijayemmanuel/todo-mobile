# shopping-web-app
This Shopping ToDo Play Slick Restful App is a very simple json rest api showing one way of using Play Framework 2.7 with [slick 3](https://github.com/slick/slick) library for database access.


It supports the following features:

* Generic Data Access layer, using [slick-repo](https://github.com/gonmarques/slick-repo)

The project was thought to be used as an activator template.

- The database pre-configured is an h2(default) and AWS RDS POSTGRES, so you just have to:

#Running

        $ sbt run

#Testing

To run all tests (routes and persistence tests):

        $ sbt test

#Using

	curl --request POST localhost:9000/buy -H "Content-type: application/json" --data "{\"name\" : \"rice\"}"

	curl localhost:9000/buylist/1

- Deploying on AWS Elastic BeanStalk with Postgres RDS


#Deployment package
        $ sbt docker:stage

        Create additional file Dockerrun.aws.json in \target\docker\stage\ with following content
        (Ref : https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/single-container-docker-configuration.html)
        It is important to specify "HostPort" which is the open HTTP port for EC2 instance
        {
           "AWSEBDockerrunVersion": "1",
           "Ports": [
               {
                   "ContainerPort": "9000",
        	        "HostPort": 80
               }
           ]
        }

        Zip the contents in \target\docker\stage\ and deploy in Elastic BeanStalk Dashboard

#Preparing RDS POSTGRES in AWS

TBD


#AWS EC2 Security group

TBD

#Credits

