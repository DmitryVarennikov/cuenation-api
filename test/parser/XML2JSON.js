var assert = require("assert"),
    fs = require('fs');

describe('parser: XML2JSON', function () {
    "use strict";

    var parser = new (require('../../src/parser/XML2JSON'));

    it('feed', function () {
        var filename = __dirname + '/../../dev/feed.xml',
            xml = fs.readFileSync(filename, {flag: 'r', encoding: 'utf8'});

        parser.feed(xml, function (err, json) {
            var expected;

            if (err) {
                assert(err);
            }



            assert.deepEqual(json, expected);
        });
    });
});
