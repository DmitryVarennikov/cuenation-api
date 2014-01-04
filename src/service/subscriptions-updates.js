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
     * Update recently added subscriptions
     *
     * @param {Function} callback
     */
    this.update = function (callback) {

    }

    /**
     * @param {Array} categories
     * @param {Date} datetime
     * @param {Function} callback
     */
    this.findNew = function (categories, datetime, callback) {

    }

}
