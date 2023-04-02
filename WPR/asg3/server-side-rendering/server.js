const express = require('express');
const mongodb = require('mongodb');
const exphbs = require('express-handlebars');

const app = express();

const hbs = exphbs.create();
app.engine('handlebars', hbs.engine);
app.set('view engine', 'handlebars');

app.use(express.static('public'));

app.use(express.urlencoded({extended: true}));
app.use(express.json());

let db = null;
async function startServer() {
    const client = await mongodb.MongoClient.connect('mongodb://localhost:27017/wpr-quiz');
    db = client.db();
    console.log('Connected to db.')

    await app.listen(3001);
    console.log('Listening on port 3001!');
}

startServer();

// middle ware
function setDb(req, res, next) {
    req.db = db;
    next();
}

app.use(setDb);

// admin space
const admin = require('./modules/admin');
app.use(admin);