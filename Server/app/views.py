from flask import render_template
from flask import jsonify
from flask import request

from app import app
import subprocess
import os

@app.route('/')
def index():
    return render_template("index.html")

@app.route('/about')
def about():
    return render_template("about.html")

@app.route('/api')
def api():
    req = request.args.get('text')
 
    cmd = "%s/scripts/text_to_image_url \"%s\"" % (os.environ['SERVER_ROOT'], req)
    url = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE).stdout.read().rstrip()
    return jsonify(
        text=req,
	url=url,
    )
