#!/bin/bash

echo "Hosts file:"
cat /etc/hosts

echo " "
figlet "Dev Essentials - UI FrontEnd"
echo " ";

sleep 1

echo "=> Starting service..."
java -jar /data/frontend/frontend-ui-products.jar --backend.retrieve.url=${BACKEND_RETRIEVE_URL} --backend.create.url=${BACKEND_CREATE_URL}