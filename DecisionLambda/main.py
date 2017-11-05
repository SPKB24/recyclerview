import json
import boto3

dynamodb = boto3.resource('dynamodb')
table = None

def get_compost_items():
    table = dynamodb.Table('COMPOSTABLE_ITEMS_TABLE')
    response = table.scan()
    return response['Items']

def get_recycle_items():
    table = dynamodb.Table('RECYCLABLE_ITEMS_TABLE')
    response = table.scan()
    return response['Items']

def parse_inputs(keywords):
    if not keywords:
        raise Exception("keywords not provided")
    return keywords.replace("_", " ").split("-")
    
def algorithm_compost(inputs, valid, output):
    for item in valid:
        print item["item_name"], item["item_name"].lower() in inputs
        print "Nothing"
        if item["item_name"].lower() in inputs:
            output["recyclable"] = False
            output["compostable"] = True
            output["item_name"] = item["item_name"]
            return output
    return output
    
def algorithm_recycle(inputs, valid, output):
    for item in valid:
        print item["item_name"], item["item_name"].lower() in inputs
        print "Nothing"
        if item["item_name"].lower() in inputs:
            output["recyclable"] = True
            output["material"] = item["material"]
            output["item_name"] = item["item_name"]
            return output
    return output

def lambda_handler(event, context):
    output = {"recyclable":False,"compostable":False,"material":None,"item_name":None}
    keywords = parse_inputs(event["keywords"])
    print keywords

    validItems = get_compost_items()
    output["valid_compost"] = validItems
    output = algorithm_compost(keywords, validItems, output)
    
    if "compostable" in output and output["compostable"] is not False:
        return output

    validItems = get_recycle_items()
    output["valid_recycle"] = validItems
    output = algorithm_recycle(keywords, validItems, output)
    
    return output
