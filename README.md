# shopping-web-app
This Shopping ToDo Play Slick Restful App is a very simple json rest api showing one way of using Play Framework 2.5 with [slick 3](https://github.com/slick/slick) library for database access.


It supports the following features:

* Generic Data Access layer, using [slick-repo](https://github.com/gonmarques/slick-repo)

The project was thought to be used as an activator template.

#Running

The database pre-configured is an h2, so you just have to:


        $ sbt run

#Testing

To run all tests (routes and persistence tests):


        $ sbt test

#Using

	curl --request POST localhost:9000/buy -H "Content-type: application/json" --data "{\"name\" : \"rice\"}"

	curl localhost:9000/buylist/1


#Credits

