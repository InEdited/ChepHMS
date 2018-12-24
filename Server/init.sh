#!/bin/bash
if [ "python -c \"\
>> try:
>>     import flask
>>     print(1)
>> except ImportError:
>>     print(0)\"" = 0 ]
then 
pip3 install flask
fi
python CreateDB.py 
echo "Created DB"
python FillDB_Random.py
echo "Filled DB"
echo "Running server aho"
python Server.py 
