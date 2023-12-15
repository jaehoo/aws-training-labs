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

    # Insert Product
    if event['path'] == '/api/products/insert':
        # Check for missing information in the request.
        if ('name' not in event['body']) or not(bodydict["name"]):
            return {'statusCode': 400, 'body': 'missing product name.'}
        if ('description' not in bodydict) or not(bodydict["description"]):
            return {'statusCode': 400, 'body': 'missing product description.'}
        if ('amount' not in event['body']) or not(bodydict["amount"]):
            return {'statusCode': 400, 'body': 'missing product amount.'}
        if ('price' not in event['body']) or not(bodydict["price"]):
            return {'statusCode': 400, 'body': 'missing product price.'}


        # Write to DynamoDB table
        # Generate ID for the item to be put
        ProductId = str(uuid.uuid4())
        ProdCreateData = str(datetime.datetime.now())

        # Put item in the DynamoDB table
        try:
            product_table.put_item(
                TableName=table_name,
                Item={
                    'pro_id': {
                        'S': ProductId
                    },
                    'pro_creation_date': {
                        'S': ProdCreateData
                    },
                    # since the item is being created, the modification is equal to creation date
                    'pro_modification_date': {
                        'S':ProdCreateData
                    },
                    'name': {
                        'S': bodydict["name"]
                    },
                    'description': {
                        'S': bodydict["description"]
                    },
                    'amount': {
                        'N': str(bodydict["amount"])
                    },
                    'price': {
                        'N': str(bodydict["price"])
                    }
                }
            )
        except:
            traceback.print_exc()
            return {'statusCode': 400, 'body': 'Error in putting item.'}
        else:
            print ('Item inserted')
            # Echo back the product ID as success message.
            resp = {
                    'message': 'PRODUCT_CREATED',
                    'productId': ProductId
                    }
            return{
                'statusCode': 200,
                'headers':{
                    "Content-type": "application/json",
                    "Access-Control-Allow-Origin": "*"
                }, 
                'body': json.dumps(resp)
            }
    #Update Product
    elif event['path'] == '/api/products/update':
    # Check for missing information in the request.
        if ('id' not in event['body']) or not(bodydict["id"]):
            return {'statusCode': 400, 'body': 'missing product id.'}
        if ('name' not in event['body']) or not(bodydict["name"]):
            return {'statusCode': 400, 'body': 'missing product  name.'}
        if ('description' not in bodydict) or not(bodydict["description"]):
            return {'statusCode': 400, 'body': 'missing product description.'}
        if ('amount' not in event['body']) or not(bodydict["amount"]):
            return {'statusCode': 400, 'body': 'missing product amount.'}
        if ('price' not in event['body']) or not(bodydict["price"]):
            return {'statusCode': 400, 'body': 'missing product price.'}
        try:
            response = product_table.get_item(
                TableName=table_name,
                Key={
                    'pro_id': {
                        'S': bodydict['id']
                    }
                }
            )
        except:
            traceback.print_exc()
            return {'statusCode': 400, 'body': 'Error in getting item.'}

        # Write to DynamoDB table
        # Generate ID for the item to be put
        ProductId = bodydict['id']
        ProdModificationData = str(datetime.datetime.now())
        #getting current value to updated without override
        ProdCreateData = str(response['Item']['pro_creation_date']['S'])
        # Put item in the DynamoDB table
        try:
            product_table.put_item(
                TableName=table_name,
                Item={
                    'pro_id': {
                        'S': ProductId
                    },
                    'pro_creation_date': {
                        'S': ProdCreateData
                    },
                    'pro_modification_date': {
                        'S':ProdModificationData
                    },
                    'name': {
                        'S': bodydict["name"]
                    },
                    'description': {
                        'S': bodydict["description"]
                    },
                    'amount': {
                        'N': str(bodydict["amount"])
                    },
                    'price': {
                        'N': str(bodydict["price"])
                    }
                }
            )
        except:
            traceback.print_exc()
            return {'statusCode': 400, 'body': 'Error in updating item.'}
        else:
            print ('Item inserted')
            # Echo back the product ID as success message.
            resp = {
                    'message': 'PRODUCT_UPDATED',
                    'productId': ProductId
                    }
            return{
                'statusCode': 200,
                'headers':{
                    "Content-type": "application/json",
                    "Access-Control-Allow-Origin": "*"
                }, 
                'body': json.dumps(resp)
            }  