import sqlite3,os

#Get the directory of the current file and connect to the database in it
dirPath = os.path.abspath(os.path.dirname(__file__))
dbPath = os.path.join(dirPath, 'Database/halls.db')
connection = sqlite3.connect(dbPath)

cursor = connection.cursor()
cursor.execute("DROP TABLE IF EXISTS schedule")
cursor.execute("DROP TABLE IF EXISTS courseIns")
cursor.execute("DROP TABLE IF EXISTS course_data")
cursor.execute("DROP TABLE IF EXISTS login")
cursor.execute("DROP TABLE IF EXISTS student_data")
cursor.execute("DROP TABLE IF EXISTS student_course")
cursor.execute("DROP TABLE IF EXISTS instructor_data")

cursor.execute('''CREATE TABLE schedule (
    day        VARCHAR 
                NOT NULL,
    slot_num   INTEGER 
                NOT NULL,
    hall_num   VARCHAR 
                NOT NULL,
    course_ins INTEGER REFERENCES courseIns (course_ins) ON DELETE CASCADE
                                                         ON UPDATE CASCADE,
    PRIMARY KEY (
        day,
        slot_num,
        hall_num 
    )
    )''')

cursor.execute('''CREATE TABLE courseIns (
    course_ins    INTEGER PRIMARY KEY 
                          AUTOINCREMENT,
    course_num    VARCHAR NOT NULL,
    instructor_id VARCHAR REFERENCES login (id) ON DELETE CASCADE
                                                ON UPDATE CASCADE,
    UNIQUE (
        course_num,
        instructor_id
    )
    )''')

cursor.execute('''CREATE TABLE course_data (
    course_num  VARCHAR PRIMARY KEY
                        UNIQUE
                        NOT NULL,
    course_name VARCHAR,
    course_description BLOB
    )''')

cursor.execute('''CREATE TABLE login(
    username VARCHAR PRIMARY KEY
                        UNIQUE
                        NOT NULL,
    password  VARCHAR NOT NULL,
    id VARCHAR UNIQUE,
    id_type VARCHAR    
    )''')

cursor.execute('''CREATE TABLE student_data(
    id VARCHAR PRIMARY KEY
                UNIQUE
                NOT NULL 
                REFERENCES login(id) ON DELETE CASCADE
                                     ON UPDATE CASCADE,
    name VARCHAR NOT NULL,
    national_number UNSIGNED BIG INT NOT NULL
    )''')

cursor.execute('''CREATE TABLE student_course(
    course_num VARCHAR NOT NULL REFERENCES course_data(course_num),
    student_id VARCHAR 
                REFERENCES student_data(id) ON DELETE CASCADE
                                            ON UPDATE CASCADE,
    year INTEGER,
    semester VARCHAR,
    grade  CHAR 
    )''')

cursor.execute('''CREATE TABLE instructor_data(
    id VARCHAR PRIMARY KEY
                UNIQUE
                NOT NULL 
                REFERENCES login(id) ON DELETE CASCADE
                                     ON UPDATE CASCADE,
    name VARCHAR NOT NULL
    )''')

connection.commit()
