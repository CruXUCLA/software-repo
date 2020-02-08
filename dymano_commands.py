from __future__ import print_function
import boto3
import json
import decimal
from botocore.exceptions import ClientError


class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if o % 1 > 0:
                return float(o)
            else:
                return int(o)
        return super(DecimalEncoder, self).default(o)


# Database Info
dynamodb = boto3.resource('dynamodb')
dynamoTable = dynamodb.Table('test_table1')

# PutItem
def putItem(name,age,id):
    response = dynamoTable.put_item(
        Item={
            'name': name,
            'age': age,
            'id': decimal.Decimal(id)
        }
    )
# print("PutItem succeeded:")
# print(json.dumps(response, indent=4, cls=DecimalEncoder))

# Get Item

def getItem(userName):
    get_name = userName

    try:
        response = dynamoTable.get_item(
            Key={
                'name': get_name
        }
    )
    except ClientError as e:
        print(e.response['Error']['Message'])
    else:
        item = response['Item']
        print("Data :")
        print(json.dumps(item, indent=4, cls=DecimalEncoder))

# Update Item

def updateItem(userName,id,age):
    insert_name = userName
    try :
        response = dynamoTable.update_item(
            Key={
                'name': insert_name,
            },
            UpdateExpression="set age=:a, id=:i",
            ConditionExpression='attribute_exists(id)',
            ExpressionAttributeValues={
                ':i': decimal.Decimal(id),
                ':a': age
             },
            ReturnValues="UPDATED_NEW"
        )
    except ClientError as e:
        print(e.response['Error']['Message'])
