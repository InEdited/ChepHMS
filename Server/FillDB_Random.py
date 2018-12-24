import sqlite3,os,random

#Get the directory of the current file and connect to the database in it
dirPath = os.path.abspath(os.path.dirname(__file__))
dbPath = os.path.join(dirPath, 'Database/halls.db')
connection = sqlite3.connect(dbPath)

c = connection.cursor()

days = ['Saturday','Sunday','Monday','Tuesday','Wednesday','Thursday']
slots = [1,2,3,4,5,6,7,8,9,10,11,12]
halls = ['901','901A','902','902A','902B',
        '904','911','911A','912','913','914','914A',
        '921','922','923','921A','924','924A',
        '931','931A','932','933',
        '941','941A','942','943','944','944A']
course_num = ['CSE115','CSE125','CSE116','CSE126','CSE127','CSE128','CSE221','CSE222','CSE223','CSE275','CSE227','CSE224','CSE225','CSE226','CSE215','CSE365','CSE335','CSE325','CSE316','CSE336','CSE326','CSE345','CSE415','CSE436','CSE425','CSE437','CSE426','CSE427']
instructors = ['instructorA','instructorB','instructorC','instructorD','instructorE','instructorF','instructorG','instructorH','instructorI',]


for course in course_num:
    c.execute('''INSERT INTO courseIns (course_num,instructor_name) VALUES (?,?)'''
     ,(course,random.choice(instructors)))
    c.execute('''INSERT INTO courseIns (course_num,instructor_name) VALUES (?,?)'''
     ,(course,random.choice(instructors)))

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

connection.commit()
connection.close()
        