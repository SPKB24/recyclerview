from flask import render_template
from flask import jsonify
from flask import request

from app import app
import subprocess
import os
import urllib2, json

from clarifai import rest
from clarifai.rest import ClarifaiApp

@app.route('/')
def index():
    return render_template("index.html")

@app.route('/about')
def about():
    return render_template("about.html")

@app.route('/text_to_material')
def api():
    app = ClarifaiApp(api_key=os.environ['CL_PASS'])
    model = app.models.get("general-v1.3")
    req = request.args.get('text')
    cmd = "%s/scripts/text_to_image_url \"%s\"" % (os.environ['SERVER_ROOT'], req)
    image_url = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE).stdout.read().rstrip()
    results = model.predict_by_url(url=image_url)
    keyword_string = ""
    for e in results['outputs'][0]['data']['concepts']:
        keyword_string += e['name'].replace(' ', '_') + '-'
    keyword_string = keyword_string[:-1]
   
    check_url = "https://e3ldzttflh.execute-api.us-east-2.amazonaws.com/api/recyclable?keywords=%s" % keyword_string
    resp = json.loads(urllib2.urlopen(check_url).read())
    return jsonify(
        text=req,
	imageUrl=image_url,
        res=results['outputs'][0]['data']['concepts'],
        recycle=resp['recyclable'],
        item=resp['item_name'],
        material=resp['material'],
        string=keyword_string,
        checkUrl=check_url
    )
