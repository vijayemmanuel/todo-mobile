# shopping-web-app
This Shopping ToDo Play Slick Restful App is a very simple json rest api showing one way of using Play Framework 2.7 with [slick 3](https://github.com/slick/slick) library for database access.


It supports the following features:

* Generic Data Access layer, using [slick-repo](https://github.com/gonmarques/slick-repo)

The project was thought to be used as an activator template.

**The database pre-configured is an h2(default) and AWS RDS POSTGRES**

#Running

        $ sbt run

#Testing

To run all tests (routes and persistence tests):

        $ sbt test

#Using

	curl --request POST localhost:9000/buy -H "Content-type: application/json" --data "{\"name\" : \"rice\"}"

	curl localhost:9000/buylist/1

**Deploying on AWS Elastic BeanStalk with Postgres RDS**

#Update the slick connection string to AWS RDS DB endpoint  in application.conf

        slick.dbs.default.driver
        slick.dbs.default.db.driver
        slick.dbs.default.db.url
        slick.dbs.default.db.user
        slick.dbs.default.db.password

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


#Postgres DB Connection Security group

The EC2 and Postgres should nomrally be in the same VPC. Open the the Incoming traffic for EC2 on the DB security group

#Credits

