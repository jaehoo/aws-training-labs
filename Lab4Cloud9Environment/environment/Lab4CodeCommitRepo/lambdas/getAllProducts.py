import os
import uuid
import json
import boto3
import traceback
from botocore.exceptions import ClientError
import datetime

def lambda_handler(event, context):

    # Get environment variables
    table_name = os.environ['TABLE']
    region = os.environ['AWS_REGION']
    product_table = boto3.client('dynamodb', region_name=region)


    # GET Method
    try:
        response = product_table.scan(
            TableName=table_name,
        )
    except ClientError as e:
        print(e.response['Error']['Message'])
        return {'statusCode': 400, 'body': e.response['Error']['Message']}
    else:
        resp = []
        for item in response['Items']:
            product = {
                'id': item['pro_id']['S'],
                'creationDate': item['pro_creation_date']['S'],
                'modificationDate': item['pro_modification_date']['S'],
                'name': item['name']['S'],
                'description': item['description']['S'],
                'amount': int(item['amount']['N']),
                'price': float(item['price']['N'])
            }
            resp.append(product)
        print("Scan succeeded:")
        #printing out the dynamodb response to better debug
        print(json.dumps(resp, indent=4, sort_keys=True))
        return {
            'statusCode': 200,
            'headers':{
                "Content-type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key",
                "Access-Control-Allow-Methods": "DELETE,GET,HEAD,OPTIONS,PATCH,POST,PUT"
            },
            'body': json.dumps(resp)}