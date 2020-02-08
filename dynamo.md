# DynamoDB 

### Resources
[Setup Via Boto3](https://www.youtube.com/watch?v=G68oSgFotZA)  
[Python Scripts](https://www.youtube.com/watch?v=G68oSgFotZA)  

### Commands
#### PutItem
```response = dynamoTable.put_item(
    Item={
        'name': "test0",
        'age': 35,
        'id': decimal.Decimal(144244)
    }
)
```
