from flask import render_template
from flask import jsonify
from flask import request

from app import app

@app.route('/')
def index():
    return render_template("index.html")

@app.route('/about')
def about():
    return render_template("about.html")

@app.route('/api')
def api():
    str = request.args.get('text')
    return jsonify(
        username="Dave",
        string=str,
        email="dave@davemachado.com",
        id="123"
    )
