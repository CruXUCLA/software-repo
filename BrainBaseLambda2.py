import json

def lambda_handler(event, context):
    # TODO implement
    print ('------------------')
    try: 
        #1. Iterate over each record
        for record in event['Records']:
            #2. Handle event type
            if record['eventName'] == 'INSERT':
                handle_insert(record)
            elif record['eventName'] == 'MODIFY':
                handle_modify(record)
            elif record['eventName'] == 'REMOVE':
                handle_remove(record) 
        print ('------------------')
    except Exception as e:
        print(e)
        print ('------------------')
        return "Oops!"
        

def handle_insert(record):
    print('Handling INSERT event')
    
    #3a Get newImage content
    newImage = record['dynamodb']['NewImage']
    
    #3b Parse userID value
    newUserID = newImage['userID']['S']
    
    #3c Print it out
    print('New row added with userID=' + newUserID)
    
    #3d Parse alpha value
    newRatio = newImage['ab_ratio']['N']
    blRatio = newImage['ab_ratio_bl']['N']
    
    #3e Check if alpha is below threshold
    if int(newRatio) <= int(blRatio):
        print('The alpha/beta ratio ' + newRatio + ' is below threshold.')
    elif int(newRatio) >= int(blRatio):
        print('The alpha/beta ratio ' + newRatio + ' is above threshold.')
    
    print('Done handling INSERT event')
    
def handle_modify(record):
    
    #3a Parse oldImage and alpha
    oldImage = record['dynamodb']['OldImage']
    oldRatio = oldImage['ab_ratio']['N']
    blRatio = oldImage['ab_ratio_bl']['N']
    
    
    #3b Parse oldImage and alpha 
    newImage = record['dynamodb']['NewImage']
    newRatio = newImage['ab_ratio']['N']
    

    #3e Check if alpha is below threshold
    if int(newRatio) >= int(blRatio):
        print('The new alpha/beta ratio ' + newRatio + ' is above threshold.')
    
    
def handle_remove(record):
    print('Handling REMOVE event')
    
    #3a Parse oldImage
    oldImage = record['dynamodb']['OldImage']
    
    #3b Parse the values
    oldUserID = oldImage['userID']['S']
    
    #3c Print it out
    print('Row removed with userID=' + oldUserID)
    
    print('Done handling REMOVE event')
    