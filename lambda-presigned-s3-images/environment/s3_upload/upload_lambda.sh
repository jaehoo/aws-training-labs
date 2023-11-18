#!/bin/bash

# This helper script will update the lambda code so you don't need to wait for the pipeline.
# Please make sure you built the lambda function before executing this script.

LAMBDA_FUNCTION_NAME=`aws lambda list-functions | jq -r '.Functions[].FunctionName' | grep S3Upload`
aws lambda update-function-code --function-name $LAMBDA_FUNCTION_NAME --zip-file fileb://target/s3-upload-1.0-SNAPSHOT.jar

STATUS=`aws lambda get-function --function-name $LAMBDA_FUNCTION_NAME | jq -r '.Configuration.LastUpdateStatus'`
echo "waiting for lambda to get active: $STATUS"

while [ $STATUS != "Successful" ];
do
    echo -n "."
    sleep 1
    STATUS=`aws lambda get-function --function-name $LAMBDA_FUNCTION_NAME | jq -r '.Configuration.LastUpdateStatus'`
done
echo ""
echo "lambda is in status: $STATUS"

