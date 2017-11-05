from flask import render_template
from flask import jsonify
from flask import request

from app import app
import subprocess
import os

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
    return jsonify(
        text=req,
	url=image_url,
        res=results['outputs'][0]['data']['concepts'],
    )
