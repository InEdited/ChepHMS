from flask import (
    Flask,
    request
)
import json
import DBUtil as db

app = Flask(__name__)

@app.route('/')
def index():
    return json.dumps(db.getEmptyHalls("Saturday",5))


@app.route('/getFreeHall', methods=['POST'])
def getHall():
    print(request.get_json())
    if not request.get_json():
        return "{\"error\": \"Not found\"}" 
    day = request.get_json()['day']
    slot = request.get_json()['slot']

    return json.dumps(db.getEmptyHalls(str(day),slot)),201


@app.route('/getCourseTime', methods=['POST'])
def getCourseTime():
    print(request.get_json())
    if not request.get_json():
        return "{\"error\": \"Not found\"}" 
    course_num = request.get_json()['course_num']

    return json.dumps(db.getCourseTimes(str(course_num))),201

@app.route('/reserveSlot', methods=['POST'])
def reserveSlot():
    #print(request.get_json())
    if not request.get_json():
        return "{\"error\": \"Not found\"}" 
    day = request.get_json()['day']
    slot = request.get_json()['slot']
    hall = request.get_json()['hall']
    course_num = request.get_json()['course_num']
    instructor_name = request.get_json()['instructor_name']

    print("Reserved hall " + hall + " on " + day + " for " + instructor_name )
    return json.dumps(db.insertCourse(day,slot,hall,course_num,instructor_name)),201


@app.route('/login',methods=['POST'])
def loginGate():
    if not request.get_json():
        return "{\"error\": \"Not found\"}" 
    username = request.get_json()['username']
    password = request.get_json()['password']
    id_type = db.getLogin(username,password)
    return json.dumps(id_type),201

@app.route('/getCourseData',methods=['POST'])
def getCourseData():
    if not request.get_json():
        return "{\"error\": \"Not found\"}" 
    course_num = request.get_json()['course_num']
    return json.dumps(db.getCourseStuff(course_num)[0]),201

if __name__ == '__main__':
    app.run(host= '0.0.0.0',port=5000)