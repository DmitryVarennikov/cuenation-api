"use strict";

var util = require('util');

process.env.TZ = 'UTC';

module.exports = {
    http:    {
        port: 8099
    },
    storage: {
        subscriptions:        {
            filename: __dirname + '/../data/subscriptions.json'
        },
        subscriptionsUpdates: {
            filename: __dirname + '/../data/subscriptions-updates.json'
        }
    },
    log:     function () {
        var date = new Date().toISOString().replace(/T.+/, ''),
            logBasename = util.format('%s.log', date);

        return __dirname + '/../log/' + logBasename;
    }
};
