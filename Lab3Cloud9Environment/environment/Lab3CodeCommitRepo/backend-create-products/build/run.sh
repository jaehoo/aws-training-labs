#!/bin/bash

echo "Hosts file:"
cat /etc/hosts

echo " "
figlet "Dev Essentials - Create Products Backend"
echo " ";

sleep 1

if [[ -n "${DB_SECRETS}" ]]; then
    SECRET_JSON=${DB_SECRETS}
else
    SECRET_JSON="GET FROM ECS VARS"
fi

DB_HOST=$(echo ${SECRET_JSON} | jq -r '.host')
DB_NAME=$(echo ${SECRET_JSON} | jq -r '.dbname')
DB_USER=$(echo ${SECRET_JSON} | jq -r '.username')
DB_PASSWORD=$(echo ${SECRET_JSON} | jq -r '.password')

echo "=> Starting service..."
java -jar /data/backend/backend-create-products.jar --rds.dbinstance.host=jdbc:mysql://${DB_HOST}:3306/${DB_NAME} --rds.dbinstance.username=${DB_USER} --rds.dbinstance.password=${DB_PASSWORD}