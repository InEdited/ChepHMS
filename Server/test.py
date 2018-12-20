import DBUtil as db 
import json 

print(json.dumps(db.getCourseTimes("CSE115")))
#print(json.dumps(db.getEmptyHalls("Saturday",5)))