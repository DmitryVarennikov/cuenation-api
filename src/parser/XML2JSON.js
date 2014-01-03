'use strict';

/**
 * @constructor
 */
function XML2JSON() {

    if (! (this instanceof XML2JSON)) {
        throw new Error('This must be an instance of XML2JSON');
    }

    /**
     * @param {String} xml
     * @param {Function} callback
     */
    this.feed = function (xml, callback) {
        var json = {};

        callback(null, json);
    }

}
