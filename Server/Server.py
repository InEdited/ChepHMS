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
def getCourse():
    print(request.get_json())
    if not request.get_json():
        return "{\"error\": \"Not found\"}" 
    course_num = request.get_json()['course_num']

    return json.dumps(db.getCourseTimes(str(course_num))),201


if __name__ == '__main__':
    app.run(debug=True)