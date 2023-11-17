# Running with Docker Compose

To run all containers, its possible to use docker-compose

## Assumptions:

* A RDS MySQL instance created with public access
* Secrets for RDS created in Secrets Manager 

## Setup:

1. Under `lab3/repos` create a `.env` file to add the database credentials

1. In a terminal, get the database credentials JSON from AWS Secrets Manager, assuming the secret id is `dev/rds/lab3`:

        aws secretsmanager get-secret-value --secret-id dev/rds/lab3 --query SecretString --output text --region us-east-1
        

1. Get the JSON returned by the command and add into the `.env` file, for example:
        
        DB_SECRETS={"username":"abc","password":"abc","engine":"mysql","host":"abc","port":3306,"dbname":"abc","dbInstanceIdentifier":"abc"}

## To build and run locally:

```
cd lab3/repos
docker-compose up
```

To test, access the URL:

```
http://localhost:7000
```

## To clean up all containers

```
$ docker container rm $(docker container ls -aq)
```


