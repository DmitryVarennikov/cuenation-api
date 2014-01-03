'use strict';

/**
 * @constructor
 */
function HTML2JSON() {

    if (! (this instanceof HTML2JSON)) {
        throw new Error('This must be an instance of HTML2JSON');
    }

    /**
     * @param {String} html
     * @param {Function} callback
     */
    this.categoriesPage = function (html, callback) {
        var json = {};

        callback(null, json);
    }

}
