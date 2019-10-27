import base64
import firebase_admin
from firebase_admin import db
import json

def update_firebase_iot(event, context):
    """Triggered from a message on a Cloud Pub/Sub topic.
    Args:
         event (dict): Event payload.
         context (google.cloud.functions.Context): Metadata for the event.
    """
    print(event)
    print(base64.b64decode(event['data']).decode('utf-8'))
    print(context)
    
    if event['attributes']['deviceRegistryId'] != 'esp8266':
        return
    
    sensor_data = base64.b64decode(event['data']).decode('utf-8')
    sensor_data_json = json.loads(sensor_data)
    sensor_data_json['timestamp'] = context.timestamp
    
    # I didn't find any way to check if the app existed without a try block,
    # so this is what we're stuck with.
    try:
        firebase_admin.initialize_app(options={
        'databaseURL': 'https://esp8266airsensor.firebaseio.com/',
        })
    except:
        # if the app is already initialized (except ValueError didn't work)
        pass
    
    AIRDATA = db.reference('esp8266airsensor')
    
    AIRDATA.push(value=sensor_data_json)