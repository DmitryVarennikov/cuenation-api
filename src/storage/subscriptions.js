'use strict';

var fs = require('fs');

/**
 * Let's keep data in file system
 * @param {String} filename
 * @constructor
 */
function StorageSubscriptions(filename) {

    if (! (this instanceof StorageSubscriptions)) {
        throw new Error('This must be an instance of StorageSubscriptions');
    }

    /**
     * @param {Function} callback
     */
    this.getData = function (callback) {
        var rawData,
            data;

        try {
            if (! fs.existsSync(filename)) {
                callback(new Error('Storage does not exist'));
            }

            rawData = fs.readFileSync(filename, {flag: 'r', encoding: 'utf8'});
            if (rawData) {
                data = JSON.parse(rawData);
            }

            callback(null, data);
        } catch (e) {
            callback(e);
        }
    }

    /**
     * @param {Object} data
     * @param {Function} callback
     */
    this.updateData = function (data, callback) {
        var rawData;

        try {
            if (data) {
                rawData = JSON.stringify(data);
                fs.writeFileSync(filename, rawData, {flag: 'w', encoding: 'utf8'});
            }

            callback(null);
        } catch (e) {
            callback(e);
        }
    }

}

module.exports = StorageSubscriptions;
