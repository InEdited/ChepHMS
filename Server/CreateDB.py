import sqlite3,os

#Get the directory of the current file and connect to the database in it
dirPath = os.path.abspath(os.path.dirname(__file__))
dbPath = os.path.join(dirPath, 'Database/halls.db')
connection = sqlite3.connect(dbPath)

cursor = connection.cursor()
cursor.execute("DROP TABLE IF EXISTS schedule")
cursor.execute("DROP TABLE IF EXISTS courseIns")
cursor.execute("DROP TABLE IF EXISTS course_name")
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
    instructor_name VARCHAR

    )''')

cursor.execute('''CREATE TABLE course_name (
    course_num  VARCHAR PRIMARY KEY
                        UNIQUE
                        NOT NULL,
    course_name VARCHAR
    )''')

connection.commit()
