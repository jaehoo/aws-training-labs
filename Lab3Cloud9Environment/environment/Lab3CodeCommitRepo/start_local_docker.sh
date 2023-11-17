#!/bin/sh

export AWS_REGION=$(curl -s http://169.254.169.254/latest/meta-data/placement/availability-zone | sed 's/\(.*\)[a-z]/\1/')

export DB_SECRETS=`aws secretsmanager get-secret-value --secret-id RDSSecret --query SecretString --region ${AWS_REGION} --output text`

echo DB_SECRETS=$DB_SECRETS > .env

source ~/.bashrc 

# TOKEN=$(curl --request PUT "http://169.254.169.254/latest/api/token" --header "X-aws-ec2-metadata-token-ttl-seconds: 3600")

# AWS_REGION=$(curl -s http://169.254.169.254/latest/dynamic/instance-identity/document --header "X-aws-ec2-metadata-token: $TOKEN" | jq -r ".region")
# setting the .env file to locally test
# RDS_SECRET=$(aws secretsmanager get-secret-value --secret-id RDSSecret --region $AWS_REGION | jq --raw-output .SecretString)
# SECRET_NAME=$(aws secretsmanager list-secrets --query 'SecretList[?starts_with(Name, `Lab3DBInstance`) == `true`].{Name:Name}' --output text --region ${AWS_REGION})
# echo DB_SECRETS=$(aws secretsmanager get-secret-value --secret-id ${SECRET_NAME} --query SecretString --output text --region $AWS_REGION) > .env

#start containers
docker-compose up