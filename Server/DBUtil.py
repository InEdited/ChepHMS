import sqlite3,os

#Get the directory of the current file and connect to the database in it
def initializeDB():
    dirPath = os.path.abspath(os.path.dirname(__file__))
    dbPath = os.path.join(dirPath, 'Database/halls.db')
    connection = sqlite3.connect(dbPath)

    return connection.cursor()

def getEmptyHalls(day,slot):
    cursor = initializeDB()
    cursor.execute('''SELECT hall_num FROM schedule WHERE course_ins=? AND day=? AND slot_num=? ''',('0',day,str(slot)))
    return cursor.fetchall()

def getCourseTimes(courseNum):
    cursor = initializeDB()
    cursor.execute('''SELECT day , slot_num , hall_num , instructor_name
                    FROM( schedule AS s INNER JOIN courseIns AS c ON s.course_ins = c.course_ins
                        )WHERE course_num=?
                    ''',(str(courseNum),))
    return cursor.fetchall()

def insertCourse(day,slot,hall,courseNum,instructor_name):
    cursor = initializeDB()
    cursor.execute('''UPDATE schedule 
                        SET course_ins = (
                            SELECT course_ins from courseIns where course_num = ? AND instructor_name = ?
                        ) 
                        WHERE day = ? AND slot_num = ? AND hall_num = ?''',(courseNum,instructor_name,day,slot,hall))
    cursor.fetchall()

def getLogin(username,enteredPassword):
    cursor = initializeDB()
    cursor.execute('''SELECT password FROM login 
                            WHERE username =? 
                            ''',(str(username),))
    hashedPasssword = cursor.fetchone()[0]
    print(hashedPasssword)
    if(enteredPassword!=hashedPasssword):
        return "fail"
    cursor.execute('''SELECT id_type FROM login
                            WHERE username=?
                            ''',(str(username),))
    return cursor.fetchall()

