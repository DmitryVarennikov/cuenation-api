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
     * @param {Function} callback
     */
    this.update = function (callback) {
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
                parser.categoriesPage(body, function (json) {
                    storage.set(json, callback);
                });
            });
        });
        // hope it's enough
        req.setTimeout(3000);
        req.on('error', callback);
        req.end();
    }

    /**
     * @param {Function} callback
     */
    this.get = function (callback) {

    }

}


module.exports = ServiceSubscriptions;
