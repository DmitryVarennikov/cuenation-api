'use strict';

var http = require('http');

/**
 * @param {StorageSubscriptionsUpdates} storage
 * @param {XML2JSON} parser
 * @constructor
 */
function ServiceSubscriptionsUpdates(storage, parser) {

    if (! (this instanceof ServiceSubscriptionsUpdates)) {
        throw new Error('This must be an instance of ServiceSubscriptionsUpdates');
    }

    /**
     * @param {Function} onRes
     * @param {Function} onErr
     * @private
     */
    this._httpRequest = function (onRes, onErr) {
        var options = {
            host:   'cuentation.com',
            port:   80,
            path:   '/feed.php',
            method: 'GET'
        };

        var req = http.request(options, function (res) {
            var body = '';

            res.setEncoding('utf8');
            res.on('data', function (chunk) {
                body += chunk;
            });
            res.on('end', function () {
                onRes(body);
            });
        });
        // hope it's enough
        req.setTimeout(3000);
        req.on('error', onErr);
        req.end();
    }

    /**
     * Update recently added subscriptions
     *
     * @param {Function} callback
     */
    this.update = function (callback) {
        var onRes = function (body) {
            parser.feed(body, function (err, json) {
                if (err) {
                    callback(err);
                } else {
                    storage.set(json, callback);
                }
            });
        }

        this._httpRequest(onRes, callback);
    }

    /**
     * @param {Array} categories
     * @param {Date} datetime
     * @param {Function} callback
     */
    this.getNew = function (categories, datetime, callback) {

    }

}
