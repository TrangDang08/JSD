from flask import Flask, redirect, render_template, request, redirect, url_for
app = Flask(__name__)

todoList = [
    {'id' : 1, 'description' : 'SS1 Assignment 1', 'status' : 'Done'},
    {'id' : 2, 'description' : 'SS1 Assignment 2', 'status' : 'Doing'},
    {'id' : 3, 'description' : 'SS1 Final', 'status' : 'Doing'}
]

def autoIncrement():
    id = int(todoList[-1]['id'])
    newId = id + 1
    id = newId
    return id

@app.route('/')
def main():
    return render_template('base.html', todoList = todoList)

@app.route('/add', methods=['POST'])
def add():
    description = request.values['itemDescription']
    todoTask = {
        'id' : autoIncrement(),
        'description' : description,
        'status' : 'Doing'
    }
    print(todoTask)
    todoList.append(todoTask)
    return redirect(url_for('main'))

@app.route('/edit', methods=['POST'])
def edit():
    choice = request.values['clickBtn']
    id = int(request.values['itemID'])
    description = request.values['itemDescription']
    # get the value list of checkbox
    statusTask = request.form.getlist('itemStatus')
    print(choice)
    if choice == 'update':
        # if the box is checked, the value list will have 1 element
        if len(statusTask) == 1:
            status = 'Done'
        # if the box is not checked, the value list will have 0 element
        else:
            status = 'Doing'
        for i in range (len(todoList)):
            if todoList[i]['id'] == id:
                todoList[i] = {
                    'id' : id,
                    'description' : description,
                    'status' : status
                } 
                print(todoList[i])
                break
        return redirect(url_for('main'))
    if choice == 'delete':
        for i in range(len(todoList)):
            if todoList[i]['id'] == id:
                del todoList[i]
                break
        return redirect(url_for('main'))

app.run()