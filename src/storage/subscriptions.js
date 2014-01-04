'use strict';

var util = require('util'),
    StorageAbstract = require('./abstract');


util.inherits(StorageSubscriptions, StorageAbstract);
/**
 * Let's keep data in file system
 * @param {String} filename
 * @constructor
 */
function StorageSubscriptions(filename) {

    if (! (this instanceof StorageSubscriptions)) {
        throw new Error('This must be an instance of StorageSubscriptions');
    }

    StorageAbstract.call(this, filename);
}

module.exports = StorageSubscriptions;
