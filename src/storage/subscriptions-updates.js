'use strict';

var util = require('util'),
    StorageAbstract = require('./abstract');

util.inherits(StorageSubscriptionsUpdates, StorageAbstract);
/**
 * Let's keep data in file system
 * @param {String} filename
 * @constructor
 */
function StorageSubscriptionsUpdates(filename) {

    if (! (this instanceof StorageSubscriptionsUpdates)) {
        throw new Error('This must be an instance of StorageSubscriptionsUpdates');
    }

    StorageAbstract.call(this, filename);
}

module.exports = StorageSubscriptionsUpdates;
