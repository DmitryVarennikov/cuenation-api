'use strict';

var cheerio = require('cheerio');

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
        var linkPrefix = 'http://cuenation.com/',
            $,
            subscriptions = [],
            subscription;

        $ = cheerio.load(html);
        $('table tr', '#main_cell').each(function () {
            var image,
                category,
                desc;
            
            subscription = {};

            image = $(this).find('td').eq(0).find('img');
            if (image.length) {
                subscription.image = linkPrefix + $(image).attr('src');
            }

            category = $(this).find('td').eq(1).find('h2 a');
            if (category.length) {
                subscription.category = linkPrefix + $(category).attr('href');
            }

            desc = $(this).find('td').eq(1).find('i');
            if (desc.length) {
                subscription.desc = $(desc).text();
            }

            subscriptions.push(subscription);
        });

        callback(null, {subscriptions: subscriptions});
    }

}

module.exports = HTML2JSON;
