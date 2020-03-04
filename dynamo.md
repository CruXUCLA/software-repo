# DynamoDB 

### Resources
[Setup Via Boto3](https://www.youtube.com/watch?v=G68oSgFotZA)  
[Python Scripts](https://www.youtube.com/watch?v=G68oSgFotZA)  

### Commands
#### putItem
```def putItem(name,age,id):
    response = dynamoTable.put_item(
        Item={
            'name': name,
            'age': age,
            'id': decimal.Decimal(id)
        }
    )
```
#### getItem
```def getItem(userName):
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
```
#### updateItem
```def updateItem(userName,id,age):
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
```
