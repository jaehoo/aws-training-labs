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


    # Print statement for debugging.
    #print(event)
    
    # Load body JSON for processing
    try:
        bodydict = json.loads(event['body'])
    except:
        return {'statusCode': 400, 'body': 'malformed JSON'}


    # Check for missing information in the request.
    if ('id' not in event['body']) or not(bodydict["id"]):
        return {'statusCode': 400, 'body': 'missing product id.'}


    # Getting the product id from the input
    ProductId = bodydict["id"]

    # Delete item in the DynamoDB table
    try:
        product_table.delete_item(
            TableName=table_name,
            Key={
                'pro_id': {
                    'S': ProductId
                }
            }
        )
    except:
        traceback.print_exc()
        return {'statusCode': 400, 'body': 'Error deleting item.'}
    else:
        print ('Item deleted')
        resp = {
                'message': 'PRODUCT_DELETED',
                'productId': ProductId
                }
        # returning the successful deletion
        return{
            'statusCode': 200,
            'headers':{
                "Content-type": "application/json",
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Headers": "Content-Type,X-Amz-Date,Authorization,X-Api-Key",
                "Access-Control-Allow-Methods": "OPTIONS,POST,GET,POST"

            }, 
            'body': json.dumps(resp)
        }  
