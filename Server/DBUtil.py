import sqlite3,os

#Get the directory of the current file and connect to the database in it
dirPath = os.path.abspath(os.path.dirname(__file__))
dbPath = os.path.join(dirPath, 'Database/halls.db')
connection = sqlite3.connect(dbPath)

cursor = connection.cursor()

def getEmptyHalls(day,slot):
    cursor.execute('''SELECT hall_num FROM schedule WHERE course_ins=? AND day=? AND slot_num=? ''',('0',day,str(slot)))
    return cursor.fetchall()

def getCourseTimes(courseNum):
    cursor.execute('''SELECT day , slot_num , instructor_name
                    FROM( schedule AS s INNER JOIN courseIns AS c ON s.course_ins = c.course_ins
                        )WHERE course_num=?
                    ''',(str(courseNum),))
    return cursor.fetchall()

print(getEmptyHalls("Saturday",1))
print(getCourseTimes("CSE227"))