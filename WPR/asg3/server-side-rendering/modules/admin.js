const express = require('express');
const mongodb = require('mongodb');
const handlebars = require('handlebars');

const router = express.Router();

// view all questions
router.get('/admin/questions', async function(req, res) {
    const questions = await req.db.collection('questions').find().toArray();
    let num = 1;

    // auto index increment handlebars helper
    handlebars.registerHelper('counter', function() {
        return num++;
    });

    res.render('admin/questions/index', {
        questions: questions,
    });
});


// search question
router.post('/admin/questions/search', async function (req, res) {
    const text = req.body.text;
    const questions = await req.db.collection('questions').find({ text: { $regex: text } }).toArray();

    if (questions === null) {
        alert('The questions do not exist!');
        return res.redirect('admin/questions/index');
    }

    let count = 1;

    // auto index increment handlebars helper
    handlebars.registerHelper('counter', function() {
        return count++;
    });

    res.render('admin/questions/index', {
        questions: questions,
    });
});

// add new question
router.get('/admin/questions/add', function(req, res) {
    res.render('admin/questions/add');
});

router.post('/admin/questions', async function(req, res) {
    const text = req.body.text;
    const answers = req.body.answers;
    const correctAnswer = req.body.correctAnswer;

    // insert into db
    const result = await req.db.collection('questions').insertOne({text, answers, correctAnswer});
    console.log(result);

    // redirect
    res.redirect('/admin/questions');
});

// edit question
router.get('/admin/questions/:id/edit', async function (req, res) {
    // check if questions exists
    const id = req.params.id;
    const question = await req.db.collection('questions').findOne({ _id: mongodb.ObjectId(id) });
    if (question === null) {
        alert('The question does not exist!');
        return res.redirect('/admin/questions?message=Question not exist!');
    }

    res.render('admin/questions/edit', { question });
});

router.post('/admin/questions/:id', async function (req, res) {
    // check if question exists
    const id = req.params.id;
    const question = await req.db.collection('questions').findOne({ _id: mongodb.ObjectId(id) });
    if (question === null) {
        return res.redirect('/admin/questions?message=Question not exist!');
    }

    // save updated
    const text = req.body.text;
    const answers = req.body.answers;
    const correctAnswer = req.body.correctAnswer;
    const result = await req.db.collection('questions').updateOne({ _id: mongodb.ObjectId(id) }, { $set: { text, answers, correctAnswer } });
    console.log(result);

    // redirect
    return res.redirect('/admin/questions?message=Updated successfully');
});

// delete question
router.post('/admin/questions/:id/destroy', async function(req, res) {
    // check if question exists
    const id = req.params.id;
    const questions = await req.db.collection('questions').findOne({_id : mongodb.ObjectId(id)});
    if (questions === null) {
        return res.redirect('/admin/questions?message=Question does not exist!');
    }

    // delete
    const result = await req.db.collection('questions').deleteOne({_id : mongodb.ObjectId(id)});
    console.log(result);

    // redirect
    return res.redirect('/admin/questions?message=Deleted successfully');
});

module.exports = router;