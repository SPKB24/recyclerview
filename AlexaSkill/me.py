import urllib2
import json

url = "http://wecyclr.net/text_to_material?text=plane"
#url = "http://bartjsonapi.elasticbeanstalk.com/api/status"
q = urllib2.Request(url)
q.add_header('User-Agent', 'Mozilla/5.0')
r = urllib2.urlopen(q).read()
print(r)
