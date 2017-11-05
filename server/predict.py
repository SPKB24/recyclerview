from clarifai import rest
from clarifai.rest import ClarifaiApp
import subprocess
import os, sys
import urllib2, json

app = ClarifaiApp(api_key=os.environ['CL_PASS'])
model = app.models.get("general-v1.3")
req = sys.argv[1]
cmd = "./text_to_image_url \"%s\"" % req
image_url = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE).stdout.read().rstrip()
results = model.predict_by_url(url=image_url)
keyword_string = ""
for e in results['outputs'][0]['data']['concepts']:
	keyword_string += e['name'].replace(' ', '_') + '-'
keyword_string = keyword_string[:-1]
check_url = "https://e3ldzttflh.execute-api.us-east-2.amazonaws.com/api/recyclable?keywords=%s" % keyword_string
resp = json.loads(urllib2.urlopen(check_url).read())
resp['text'] = req
resp['imageUrl'] = image_url
resp['results'] = results['outputs'][0]['data']['concepts']
resp['recycle'] = resp['recyclable']
resp['item'] = resp['item_name']
resp['material'] = resp['material']
resp['keywordString'] = keyword_string
resp['checkUrl'] = check_url

print json.dumps(resp)
