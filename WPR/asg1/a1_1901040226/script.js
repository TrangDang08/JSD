// TODO(you): Write the JavaScript necessary to complete the assignment.
const firstScreen = document.querySelector('.first-screen');
const attempQuiz = document.querySelector('.attempt-quiz');
const reviewQuiz = document.querySelector('.review-quiz');
const button1 = document.querySelector('.blue-button-1');
const button2 = document.querySelector('.green-button');
const button3 = document.querySelector('.blue-button-2');
let attemptID;

function onJson(data) {
    console.log(data);
    attemptID = data._id;
    populateQuestion(data);
}

function onResponse(response) {
    return response.json();
}

function hideScreen() {
    firstScreen.classList.toggle('hidden');
    attempQuiz.classList.toggle('hidden');

    fetch('https://wpr-quiz-api.herokuapp.com/attempts', {
        method: 'POST',
        header: {
            'Content-Type': 'application/json'
        }
    }).then(onResponse).then(onJson)
}

let response1;

function hideScreen2() {
    const confirmWindow = confirm('Do you want to submit?');
    if (confirmWindow) {
        attempQuiz.classList.toggle('hidden');
        reviewQuiz.classList.toggle('hidden');

        fetch('https://wpr-quiz-api.herokuapp.com/attempts/'+ attemptID + '/submit', {
            method: 'POST',
            header: {
                'Content-Type': 'application/json'
            }
        }).then(onResponse).then(responseJs => {
            res = responseJs;
            console.log(response1);
            getPoint;
        })
    }
    return false;
}

function hideScreen3() {
    reviewQuiz.classList.toggle('hidden');
    firstScreen.classList.remove('hidden');
}

button1.addEventListener('click', hideScreen);
button2.addEventListener('click', hideScreen2);
button3.addEventListener('click', hideScreen3);


const choiceList = attempQuiz.querySelector('.choice-list');

function populateQuestion(data) {
    const listOfChoice = data.questions;
    for (index in listOfChoice) {
        const question = listOfChoice[index];
        id = question._id;
        answer = question.answers;
        text = question.text;

        const choiceList2 = document.createElement('div');
        choiceList2.classList.add('choice-list-small');

        const quesNum = document.createElement('h1');
        quesNum.textContent = 'Question ' + (parseInt(index)+1) + ' of ' + listOfChoice.length;
        choiceList2.appendChild(quesNum);

        const quesContent = document.createElement('p');
        quesContent.innerText = text;
        choiceList2.appendChild(quesContent);

        const ansBox = document.createElement('form');
        ansBox.classList.add('answers-box');

        for (index1 in answer) {
            const label = document.createElement('label');
            label.classList.add('option');
            label.setAttribute('for', id+'_'+index1);

            const ans1 = answer[index1];
            const radioBtn = document.createElement('input');
            radioBtn.setAttribute('type', 'radio');
            radioBtn.setAttribute('name', 'answer');
            radioBtn.setAttribute('value', ans1);
            radioBtn.setAttribute('id', id+'_'+index1);
            label.appendChild(radioBtn);
            
            const labelText = document.createElement('div');
            labelText.classList.add('text');
            labelText.innerText = ans1;
            label.appendChild(labelText);
            label.onmouseover = function() {
                label.classList.add('hover');
            }
            label.onmouseout = function() {
                label.classList.remove('hover');
            }
            
            const ansCon = document.createElement('div');
            ansCon.classList.add('answer-container');
            ansCon.appendChild(label);

            ansBox.appendChild(ansCon);

            choiceList2.appendChild(ansBox);
        }
        choiceList.appendChild(choiceList2);
        ansBox.addEventListener('change', (event) => {
            const input = event.target,
            label = input.parentElement;
            
            const prev = ansBox.querySelector('label.checked input[name="answer"]');
            if (prev) {
                input.checked = false;
                prev.parentElement.classList.remove('checked');
            }
            input.checked = true;
            label.classList.add('checked');
        }, false);
    }
}

function getPoint() {
    attempQuiz.appendChild(choiceList);
    const buttonBox = attempQuiz.querySelector('.box');
    attempQuiz.appendChild(buttonBox);

    const pointBox = document.createElement('div');
    pointBox.classList.add('box');
    reviewQuiz.appendChild(pointBox);

    const resultHeading = document.createElement('div');
    resultHeading.classList.add('result-heading');

    const resultText = document.createElement('h1');
    resultText.textContent = 'Result:';
    resultHeading.appendChild(resultText);
    const point = document.createElement('p');
    point.classList.add('point');
    point.textContent = res.score + '/' + res.question.length;
    resultHeading.appendChild(point);
    pointBox.appendChild(resultHeading);

    const pointPer = document.createElement('p');
    pointPer.classList.add('percentage');
    pointPer.textContent = (res.score/res.question.length)*100 + '%';
    pointBox.appendChild(pointPer);
    const comment = document.createElement('p');
    comment.classList.add('comment');
    comment.textContent = res.scoreText;
    pointBox.appendChild(comment);

    const buttonContainer = document.createElement('div');
    buttonContainer.classList.add('button-container');
    const button3 = document.createElement('button');
    button3.classList.add('blue-button-2');
    button3.textContent = 'Try again';
    buttonContainer.appendChild(button3);
    pointBox.appendChild(buttonContainer);
}