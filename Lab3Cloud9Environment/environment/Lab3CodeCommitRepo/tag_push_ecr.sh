#!/bin/sh
AWS_REGION=$(curl -s http://169.254.169.254/latest/meta-data/placement/availability-zone | sed 's/\(.*\)[a-z]/\1/')
source ~/.bashrc 

#getting the account 12-digit number 
ACCOUNT_NUMBER=$(aws sts get-caller-identity --query Account --output text)

# ecr docker login
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ACCOUNT_NUMBER.dkr.ecr.$AWS_REGION.amazonaws.com

#tagging and pushing create-products 
docker tag dev-essentials/lab3/backend-create-products:latest ${ACCOUNT_NUMBER}.dkr.ecr.${AWS_REGION}.amazonaws.com/dev-essentials/lab3/backend-create-products:latest
docker push ${ACCOUNT_NUMBER}.dkr.ecr.${AWS_REGION}.amazonaws.com/dev-essentials/lab3/backend-create-products:latest

#tagging and pushing retrieve-products 
docker tag dev-essentials/lab3/backend-retrieve-products:latest ${ACCOUNT_NUMBER}.dkr.ecr.${AWS_REGION}.amazonaws.com/dev-essentials/lab3/backend-retrieve-products:latest
docker push ${ACCOUNT_NUMBER}.dkr.ecr.${AWS_REGION}.amazonaws.com/dev-essentials/lab3/backend-retrieve-products:latest

#tagging and pushing frontend-ui-products 
docker tag dev-essentials/lab3/frontend-ui-products:latest ${ACCOUNT_NUMBER}.dkr.ecr.${AWS_REGION}.amazonaws.com/dev-essentials/lab3/frontend-ui-products:latest
docker push ${ACCOUNT_NUMBER}.dkr.ecr.${AWS_REGION}.amazonaws.com/dev-essentials/lab3/frontend-ui-products:latest