#!/bin/sh

export AWS_REGION=$(curl -s http://169.254.169.254/latest/meta-data/placement/availability-zone | sed 's/\(.*\)[a-z]/\1/')

export DB_SECRETS=`aws secretsmanager get-secret-value --secret-id RDSSecret --query SecretString --region ${AWS_REGION} --output text`

echo DB_SECRETS=$DB_SECRETS > .env

source ~/.bashrc 


# build create-products
cd ~/environment/Lab3CodeCommitRepo/backend-create-products
mvn clean package
docker build -t dev-essentials/lab3/backend-create-products .

# build retrieve-products
cd ~/environment/Lab3CodeCommitRepo/backend-retrieve-products
mvn clean package
docker build -t dev-essentials/lab3/backend-retrieve-products .

#build frontend-ui-products
cd ~/environment/Lab3CodeCommitRepo/frontend-ui-products
mvn clean package
docker build -t dev-essentials/lab3/frontend-ui-products .

cd ~/environment/Lab3CodeCommitRepo/
docker images | grep dev-essentials
