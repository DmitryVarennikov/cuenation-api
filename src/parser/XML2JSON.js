'use strict';

var cheerio = require('cheerio');

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
        var $,
            updates = [],
            json;

        $ = cheerio.load(xml);
        $('channel item').each(function () {
            var categoryExists,
                titleExists,
                pubDateExists,
                pubDate;

            categoryExists = $(this).find('category').length > 0;
            titleExists = $(this).find('title').length > 0;
            pubDateExists = $(this).find('pubDate').length > 0;

            if (categoryExists && titleExists && pubDateExists) {
                updates.push({
                    category: $(this).find('category').text(),
                    title:    $(this).find('title').text(),
                    date:     new Date($(this).find('pubDate').text()).toJSON()
                });
            }
        });

        json = {
            updates_count: updates.length,
            updates:       updates
        };

        callback(null, json);
    }

}

module.exports = XML2JSON;
