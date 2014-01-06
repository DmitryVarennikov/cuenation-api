'use strict';

var http = require('http');

/**
 * @param {StorageSubscriptions} storage
 * @param {HTML2JSON} parser
 * @constructor
 */
function ServiceSubscriptions(storage, parser) {

    if (! (this instanceof ServiceSubscriptions)) {
        throw new Error('This must be an instance of ServiceSubscriptions');
    }

    /**
     * For test purpose
     *
     * @param {Function} onRes
     * @param {Function} onErr
     * @private
     */
    this._httpRequest = function (onRes, onErr) {
        var options = {
            host:   'cuentation.com',
            port:   80,
            path:   '/?page=categories',
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
     * @param {Function} callback
     */
    this.update = function (callback) {
        var onRes = function (body) {
            parser.categoriesPage(body, function (err, json) {
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
     * @param {Function} callback
     */
    this.get = function (callback) {
        storage.get(function (err, json) {
            var data;

            data = {
                subscriptions: json
            };

            callback(err, data);
        });
    }

}


module.exports = ServiceSubscriptions;
