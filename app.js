var express = require('express'),
    app = express(),
    util = require('util'),
    fs = require('fs'),
    path = require('path'),
    clc = require('cli-color'),
    config = require('./config/index');





// unfortunately current implementation of EventEmitter doesn't allow you to listen to any event,
// so we have to subscribe to all the desired events explicitly
//worker.on('debug', function(message) {
//    log('DEBUG', message);
//});
//worker.on('error', function(message) {
//    log('ERROR', message);
//});

function log(severity, message) {
    var date = new Date().toISOString().replace(/T/, ' ').replace(/\..+/, ''),
        data = util.format('[%s] event.%s: %s\n', date, severity, message);

    fs.writeFileSync(config.log(), data, {flag: 'a'});
    if ('ERROR' == severity) {
        console.log(clc.red(data));
    } else {
        console.log(clc.blackBright(data));
    }
}

function errorHandler(err, req, res, next) {
    res.status(404);
    res.render('error', {message: err.message});
}

app.use(errorHandler);
app.use(express.json());
app.use(express.urlencoded());

app.get('/', function(req, res, next) {
    // just make sure we work
    res.send('Hello world!');
});

app.listen(config.http.port);
console.log('Listen on port ' + config.http.port);

