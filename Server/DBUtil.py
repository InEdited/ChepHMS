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
    cursor.execute('''SELECT day , slot_num , hall_num , id.name AS instructor_name
                    FROM( 
                        (schedule AS s 
                            INNER JOIN courseIns AS c 
                            ON s.course_ins = c.course_ins) 
                                INNER JOIN instructor_data AS id 
                                ON c.instructor_id = id.id )
                            WHERE course_num=?
                    ''',(str(courseNum),))
    timez = cursor.fetchall()
    #items = [dict(zip([key[0] for key in cursor.description],row)) for row in timez]
    return timez

def insertCourse(day,slot,hall,courseNum,instructor_name):
    cursor = initializeDB()
    cursor.execute('''INSERT 
                        INTO courseIns (course_num,instructor_id) 
                      VALUES (?,?);''',(courseNum,instructor_name,))
    cursor.execute('''
                      UPDATE schedule 
                         SET course_ins = (SELECT course_ins 
                        FROM courseIns 
                       WHERE course_num = ? 
                         AND instructor_id = ?) 
                       WHERE day = ? 
                         AND slot_num = ? 
                         AND hall_num = ? ''',(courseNum,instructor_name,day,slot,str(hall),))
    cursor.connection.commit()

def getLogin(username,enteredPassword):
    cursor = initializeDB()
    cursor.execute('''SELECT password FROM login 
                            WHERE username =? 
                            ''',(str(username),))
    hashedPasssword = cursor.fetchone()[0]
    if(enteredPassword!=hashedPasssword):
        return "fail"
    cursor.execute('''SELECT id_type FROM login
                            WHERE username=?
                            ''',(str(username),))
    return cursor.fetchall()

def getCourseStuff(courseNum):
    cursor = initializeDB()
    cursor.execute('''SELECT course_name, course_description,  id.name as instructor_name
                             FROM(
                                 (course_data AS c 
                                    INNER JOIN courseIns AS ci 
                                         ON c.course_num = ci.course_num)
                                    INNER JOIN instructor_data AS id 
                                        ON ci.instructor_id = id.id) 
                       WHERE c.course_num = ?''',(str(courseNum),))
    dataz = cursor.fetchall()
    items = [{cursor.description[0][0] : dataz[0][0] 
            ,cursor.description[1][0] : dataz[0][1]
            ,cursor.description[2][0] : [row[2] for row in dataz]}]
    
    return items

def getTaughtCourses(username):
    cursor = initializeDB()
    cursor.execute('''SELECT course_num AS courseNum
                        FROM courseIns 
                       WHERE instructor_id = (SELECT id 
                        FROM login 
                       WHERE username = ? )''',(username,))
    return cursor.fetchall()


def getCourseStudents(courseNum):
    cursor = initializeDB()
    cursor.execute('''SELECT name                    AS student_name,
                             student_id,
                             grade
                        FROM (student_course    AS sc 
                                INNER JOIN student_data AS s 
                                ON sc.student_id = s.id) 
                       WHERE course_num=?''',(courseNum,) )
    dataz =  cursor.fetchall()
    items = [dict(zip([key[0] for key in cursor.description],row)) for row in dataz]
    return items 

def getCoursesRegistered(username):
    cursor = initializeDB()
    cursor.execute('''SELECT course_num , year , semester , grade
                        FROM student_course
                       WHERE student_id = (SELECT id 
                        FROM login 
                       WHERE username = ? )''',(username,))
    return cursor.fetchall()
