import sqlite3
import os
import random
import hashlib
import lorem

#Get the directory of the current file and connect to the database in it
dirPath = os.path.abspath(os.path.dirname(__file__))
dbPath = os.path.join(dirPath, 'Database/halls.db')
connection = sqlite3.connect(dbPath)

c = connection.cursor()
def hashDis(thing):
    thing = str(thing)
    return hashlib.sha256((thing.encode())).hexdigest()

days = ['Saturday','Sunday','Monday','Tuesday','Wednesday','Thursday']
slots = [1,2,3,4,5,6,7,8,9,10,11,12]
halls = ['901','901A','902','902A','902B',
        '904','911','911A','912','913','914','914A',
        '921','922','923','921A','924','924A',
        '931','931A','932','933',
        '941','941A','942','943','944','944A']
courses_dict = {
    "CSE115" : "Digital Design", 
    "CSE116" : "Computer Architecture", 
    "CSE125" : "Computer Programming (1)", 
    "CSE126" : "Computer Programming (2)", 
    "CSE127" : "Data Structures and Algorithms", 
    "CSE128" : "Software Engineering (1)", 
    "ECE141" : "Electrical and Electronic Circuits", 
    "CSE215" : "Electronic Design Automation", 
    "CSE221" : "Object-Oriented Analysis and Design", 
    "CSE222" : "Software Engineering (2)", 
    "CSE223" : "Operating Systems", 
    "CSE224" : "Design and Analysis of Algorithms", 
    "CSE225" : "Software Testing, Validation, and Verification", 
    "CSE226" : "Design of Compilers", 
    "CSE227" : "Database Systems (1)", 
    "ECE255" : "Signals and Systems", 
    "CSE275" : "Control Engineering", 
    "CSE316" : "Microcontrollers and Interfacing", 
    "CSE325" : "Agile Software Engineering", 
    "CSE326" : "Software Formal Specifications", 
    "CSE335" : "Computer Networks", 
    "CSE336" : "Distributed Computing", 
    "CSE345" : "Real-Time and Embedded Systems Design", 
    "CSE365" : "Computer Vision", 
    "CSE415" : "High-Performance Computing", 
    "CSE425" : "Software Design Patterns", 
    "CSE426" : "Software Maintenance and Evolution", 
    "CSE427" : "Software Project Management", 
    "CSE436" : "Computer and Network Security", 
    "CSE437" : "Mobile Computing", 
    "CSE496" : "Graduation Project (1)", 
    "CSE497" : "Graduation Project (2)" 
}
instructors = {1:'instructorA',2:'instructorB',3:'instructorC',4:'instructorD',5:'instructorE',6:'instructorF',7:'instructorG',8:'instructorH',9:'instructorI'}

username_passwords = {("InEdited","instructor","16p6071") : hashDis('123456789'),("mo7sen","student","16p8207"):hashDis('987654321'), ("lolidk","instructor","4"):hashDis('aylmao') }

for course,name in courses_dict.items():
    ra = random.randint(2,8)
    c.execute('''INSERT INTO courseIns (course_num,instructor_id) VALUES (?,?)'''
     ,(course,ra))
    c.execute('''INSERT INTO courseIns (course_num,instructor_id) VALUES (?,?)'''
     ,(course,ra-1))
    c.execute('''INSERT INTO course_data VALUES (?,?,?)''',(course,name,lorem.paragraph()))
    

for day in days:
    for slot in slots:
        numlist = []
        for hall in halls:
            if(random.randint(5,10)==7):
                c.execute('''INSERT INTO schedule VALUES (?,?,?,?)''',
                (day,
                slot,
                hall,
                0)
                )
            else:
                rnd = random.randint(1,56)
                while(rnd in numlist):
                    rnd = random.randint(1,56)
                numlist.append(rnd)
                c.execute('''INSERT INTO schedule VALUES (?,?,?,?)''',
                (day,
                slot,
                hall,
                rnd)
                )

for user, password in username_passwords.items():
    c.execute('''INSERT 
                   INTO login 
                 VALUES (?,?,?,?) ''',(str(user[0]),str(password),str(user[2]),str(user[1])))

for id,name in instructors.items():
     c.execute('''INSERT 
                    INTO instructor_data 
                  VALUES (?,?) ''',(str(id),str(name)))
     

#randomly manually populating student data and student course
c.execute('''INSERT INTO student_data values (?,?,?)''',("16p8207","Ruby",5555555555))

c.execute('''INSERT INTO student_course values (?,?,?,?,?)''',("CSE115","16p8207",2018,"fall","A"))
c.execute('''INSERT INTO student_course values (?,?,?,?,?)''',("CSE116","16p8207",2018,"fall","B"))
c.execute('''INSERT INTO student_course values (?,?,?,?,?)''',("CSE227","16p8207",2018,"fall","C"))
c.execute('''INSERT INTO student_course values (?,?,?,?,?)''',("CSE222","16p8207",2018,"fall","B"))
c.execute('''INSERT INTO student_course values (?,?,?,?,?)''',("CSE223","16p8207",2018,"fall","D"))


connection.commit()
connection.close()
        