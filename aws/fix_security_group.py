import json
import time
import boto3
import botocore
import traceback
from botocore.vendored import requests

SUCCESS = 'SUCCESS'
FAILED = 'FAILED'


def handler(event=None, context=None):
  try:
    session = create_new_session(event)
    client = session.client('ec2')
    ports = [80, 443, 3389]
    security_groups = client.describe_security_groups()

    for group in security_groups['SecurityGroups']:
      for port in ports:
        try:
          client.authorize_security_group_ingress(
            CidrIp='0.0.0.0/0',
            FromPort=port,
            GroupId=group['GroupId'],
            IpProtocol='tcp',
            ToPort=port,
          )
        except Exception as e:
          print(e)
          continue

    send(event, context, SUCCESS, {'Response': 'Success'})
    return True

  except Exception as e:
    print('Failed to run custom resource')
    print(traceback.format_exc())
    if 'ResponseURL' in event.keys():
      send(event, context, FAILED, {'Response': 'Failure'})
      return
    else:
      return False


def create_new_session(event):
  if not event:
    return credential_helper.create_new_session()
  else:
    return boto3.Session(
      region_name='us-west-2'
    )


def send(event, context, responseStatus, responseData, physicalResourceId=None, noEcho=False):
  response_url = event['ResponseURL']

  print(response_url)

  response_body = {}
  response_body['Status'] = responseStatus
  response_body['Reason'] = 'See the details in CloudWatch Log Stream: ' + context.log_stream_name
  response_body['PhysicalResourceId'] = physicalResourceId or context.log_stream_name
  response_body['StackId'] = event['StackId']
  response_body['RequestId'] = event['RequestId']
  response_body['LogicalResourceId'] = event['LogicalResourceId']
  response_body['NoEcho'] = noEcho
  response_body['Data'] = responseData

  json_response_body = json.dumps(response_body)

  print('Response body: ' + json_response_body)

  headers = {
    'content-type': '',
    'content-length': str(len(json_response_body))
  }

  try:
    response = requests.put(response_url,
                            data=json_response_body,
                            headers=headers)
    print('Status code: ' + response.reason)
  except Exception as e:
    print('send(..) failed executing requests.put(..): ' + str(e))