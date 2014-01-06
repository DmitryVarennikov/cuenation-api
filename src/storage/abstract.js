'use strict';

var fs = require('fs');

/**
 * Let's keep data in file system
 * @param {String} filename
 * @constructor
 */
function StorageAbstract(filename) {

    if (! (this instanceof StorageAbstract)) {
        throw new Error('This must be an instance of StorageAbstract');
    }

    this._filename = filename;
}

/**
 * @param {Function} callback
 */
StorageAbstract.prototype.get = function (callback) {
    var err,
        rawData,
        data = {};

    try {
        if (! fs.existsSync(this._filename)) {
            err = new Error('Storage does not exist');
            err.id = 'storageDoesNotExist';
            callback(err);
        } else {
            fs.readFile(this._filename, {flag: 'r', encoding: 'utf8'}, function (err, rawData) {
                if (err) {
                    callback(err);
                }

                try {
                    if (rawData) {
                        data = JSON.parse(rawData);
                    }

                    callback(null, data);
                } catch (err) {
                    callback(err);
                }
            });
        }
    } catch (e) {
        callback(e);
    }
}

/**
 * @param {Object} data
 * @param {Function} callback
 */
StorageAbstract.prototype.set = function (data, callback) {
    var rawData;

    if (data) {
        try {
            rawData = JSON.stringify(data);
            fs.writeFile(this._filename, rawData, {flag: 'w', encoding: 'utf8'}, callback);
        } catch (err) {
            callback(err);
        }
    } else {
        callback(null);
    }
}

module.exports = StorageAbstract;
