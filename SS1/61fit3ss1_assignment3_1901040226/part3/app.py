from flask import Flask, redirect, render_template, request, redirect, url_for
import pymongo
from bson import ObjectId

app = Flask(__name__)

myclient = pymongo.MongoClient('mongodb://localhost:27017/')
mydb = myclient['todo']
todoList = mydb['tasks']

@app.route('/')
def main():
    todoArray = todoList.find()
    todoArray2 = []
    for data in todoArray:
        todoArray2.append(data)
    for i in todoArray2:
        print(i)
    # change object id type into string for getting later
    for item in range (len(todoArray2)):
        todoArray2[item]['_id'] = str(todoArray2[item]['_id'])
    return render_template('base.html', todoList = todoArray2)

@app.route('/api/add', methods=['POST'])
def add():
    description = request.values['itemDescription']
    todoTask = {
        'description' : description,
        'status' : 'Doing'
    }
    todoList.insert_one(todoTask)
    return redirect(url_for('main'))

@app.route('/edit', methods=['POST'])
def edit():
    choice = request.values['clickBtn']
    id = ObjectId(request.values['itemID'])
    description = request.values['itemDescription']
    # get the value list of checkbox
    statusTask = request.form.getlist('itemStatus')
    filter = { '_id': id }
    print(choice)
    if choice == 'update':
        # if the box is checked, the value list will have 1 element
        if len(statusTask) == 1:
            status = 'Done'
        # if the box is not checked, the value list will have 0 element
        else:
            status = 'Doing'
        update = { 
            '$set': {
                "description": description,
                'status': status
            } 
        }
        todoList.update_one(filter, update)
        return redirect(url_for('main'))
    if choice == 'delete':
        todoList.delete_one(filter)
        return redirect(url_for('main'))

app.run()