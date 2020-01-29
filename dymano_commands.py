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
response = dynamoTable.put_item(
    Item={
        'name': "test0",
        'age': 35,
        'id': decimal.Decimal(144244)
    }
)

# print("PutItem succeeded:")
# print(json.dumps(response, indent=4, cls=DecimalEncoder))

# Get Item
get_name = "test1"

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
insert_name = "test2"

response = dynamoTable.update_item(
    Key={
        'name': insert_name,
    },
    UpdateExpression="set id = :i, age=:a",
    ExpressionAttributeValues={
        ':i': decimal.Decimal(333222),
        ':a': 21
    },
    ReturnValues="UPDATED_NEW"
)

print("Update Item succeeded")
# print(json.dumps(response, indent=4, cls=DecimalEncoder))
