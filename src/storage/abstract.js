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
        data;

    try {
        if (! fs.existsSync(this._filename)) {
            err = new Error('Storage does not exist');
            err.id = 'storageDoesNotExist';
            callback(err);
        } else {
            rawData = fs.readFileSync(this._filename, {flag: 'r', encoding: 'utf8'});
            if (rawData) {
                data = JSON.parse(rawData);
            }

            callback(null, data);
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

    try {
        if (data) {
            rawData = JSON.stringify(data);
            fs.writeFileSync(this._filename, rawData, {flag: 'w', encoding: 'utf8'});
        }

        callback(null);
    } catch (e) {
        callback(e);
    }
}

module.exports = StorageAbstract;
