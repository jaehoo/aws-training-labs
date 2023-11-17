# Retrieve Products Backend

This is a Java Docker based backend app to be deployed using ECS as an example of microservice

## To compile the application:

```
mvn clean install
```

## To build and run locally with docker its required 2 steps:

* Create a secret in AWS Secrets Manager, choose `Credentials for RDS database` and choose an existing RDS instance
* Pass the secrets coming through AWS Secrets Manager. For this, you will need an awscli installed and configured
    pointing to an existing AWS account.

After that run the command, assuming the secrets name is `dev/rds/lab3`:

```
docker build -t dev-essentials/lab3/backend-retrieve-products . && docker run -p 5000:8080 -e DB_SECRETS=$(aws secretsmanager get-secret-value --secret-id dev/rds/lab3 --query SecretString --output text --region us-east-1) dev-essentials/lab3/backend-retrieve-products
```

To test, make a GET request to `http://localhost:5000/lab3/v1/backend/retrieve/products`:

```
curl -X GET http://localhost:5000/lab3/v1/backend/retrieve/products | jq
```

INFO: `jq` is a utility tool to handle and beautify JSON payloads.

## To stop all running containers and clean up:

```
$ docker stop $(docker container ls -aq) && docker container rm $(docker container ls -aq)
```
