var express = require('express'),
    app = express(),
    util = require('util'),
    fs = require('fs'),
    path = require('path'),
    clc = require('cli-color'),
    config = require('./config/index');

var subscriptionsStorage = new (require('./src/storage/subscriptions'))(config.storage.subscriptions.filename),
    subscriptionsUpdatesStorage = new require('./src/storage/subscriptions')(config.storage.subscriptionsUpdates.filename),
    htmlParser = new require('./src/parser/HTML2JSON')(),
    xmlParser = new require('./src/parser/XML2JSON')(),
    serviceSubscriptions = new require('./src/service/subscriptions')(subscriptionsStorage, htmlParser),
    serviceSubscriptionsUpdates = new require('./src/service/subscriptions-updates')(subscriptionsUpdatesStorage, xmlParser);


function errorHandler(err, req, res, next) {
    res.status(404);
    res.render('error', {message: err.message});
}

app.use(errorHandler);
app.use(express.json());
app.use(express.urlencoded());

app.get('/', function (req, res, next) {
    // just make sure we're up
    res.send('Hello world!');
});

app.get('/subscriptions.json', function (req, res, next) {
    serviceSubscriptions.get(function (err, data) {
        var out;
        if (err) {
            out = {
                status: false,
                err:    err
            };
        } else {
            out = {
                status: true,
                data:   data
            };
        }

        res.json(out);
    });
});

app.get('/subscriptions-updates.json', function (req, res, next) {
    var categories,
        date;

    categories = req.param('categories', []);
    if (! (categories instanceof Array)) {
        categories = [String(categories)];
    }

    date = new Date(req.param('last-update', ''));
    if (! (date instanceof Date) || ! date.toJSON()) {
        date = new Date();
    }

    serviceSubscriptionsUpdates.getNew(categories, date, function (err, data) {
        var out;
        if (err) {
            out = {
                status: false,
                err:    err
            };
        } else {
            out = {
                status: true,
                data:   data
            };
        }

        res.json(out);
    });
});


app.listen(config.http.port);
console.log('Listen on port ' + config.http.port);

