# Products UI Frontend

This is a Java Docker based frontend app to be deployed using ECS as an example of microservice

## To compile the application:

```
mvn clean install
```

## To build and run locally:

```
docker build -t dev-essentials/lab3/frontend-ui-products . && docker run -p 7000:8080 -e BACKEND_RETRIEVE_URL=http://localhost:5000 -e BACKEND_CREATE_URL=http://localhost:6000 dev-essentials/lab3/frontend-ui-products
```

To test, access the URL `http://localhost:7000/`:

## To stop all running containers and clean up:

```
$ docker stop $(docker container ls -aq) && docker container rm $(docker container ls -aq)
```
