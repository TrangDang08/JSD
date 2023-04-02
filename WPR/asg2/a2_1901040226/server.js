const express = require('express');
const mongodb = require('mongodb');

const app = express();

app.use(express.urlencoded({extended: true}));
app.use(express.json());

let db = null;
async function startServer() {
    const client = await mongodb.MongoClient.connect('mongodb://localhost:27017/wpr-quiz');
    db = client.db();
    console.log('Connected to db.')

    app.listen(4000, function() {
        console.log('Listening to port 4000!');
    });
}

startServer();

app.post('/attempts', async function(req, res) {
    const questions = await db.collection('questions').aggregate([
        { $sample: {size: 10} },
        { $project: {
            _id: 1,
            answers: 1,
            text: 1,
            correctAnswer: 1 }
        }
    ]).toArray();

    const correctAnswerContainer = {};
    for (const question of questions) {
        correctAnswerContainer[question._id] = question.correctAnswer;
        delete question.correctAnswer;
    }

    const result = {
        questions: questions,
        complete: false,
        score: 0,
        correctAnswers: correctAnswerContainer,
        startedAt: new Date()
    };
    await db.collection('temp').insertOne(result);
    delete result.correctAnswers;

    res.status(201).json(result);
});

app.post('/attempts/:id/submit', async function(req, res) {
    const _id = req.params.id;
    const filter = {_id: mongodb.ObjectId(_id)};
    const record = await db.collection('temp').findOne(filter);
    
    let ansReq;
    if (record.complete === false) {
        ansReq = req.body.answers;
    } else {
        ansReq = record.answers;
    }

    const scoring = record.correctAnswers;
    let scoreNum = 0;
    for (i in ansReq) {
        if (scoring[i] == ansReq[i]) {
            scoreNum++;
        }
    }
    let scoreText = "";
    if (scoreNum >= 9 && scoreNum <= 10) {
        scoreText = "Perfect!!";
    } else if (scoreNum >= 7 && scoreNum < 9) {
        scoreText = "Well done!";
    } else if (scoreNum >= 5 && scoreNum < 7) {
        scoreText = "Good, keep up!";
    } else {
        scoreText = "Practice more to improve it :D";
    }

    const update = {
        $set: {
            complete: true,
            score: scoreNum,
            answers: ansReq,
            scoreText: scoreText
        }
    };
    const upsert = true;
    await db.collection('temp').updateOne(filter, update, upsert);

    const result = await db.collection('temp').findOne(filter);
    res.status(200).json(result);
});