'use strict';

var fs = require('fs');

/**
 * Let's keep data in file system
 * @param {String} filename
 * @constructor
 */
function StorageSubscriptionsUpdates(filename) {

    if (! (this instanceof StorageSubscriptionsUpdates)) {
        throw new Error('This must be an instance of StorageSubscriptionsUpdates');
    }

}
