var assert = require("assert"),
    fs = require('fs');

describe('parser: XML2JSON', function () {
    "use strict";

    var parser = new (require('../../src/parser/XML2JSON'));

    it('feed', function (done) {
        var filename = __dirname + '/../../dev/feed.xml',
            xml = fs.readFileSync(filename, {flag: 'r', encoding: 'utf8'});

        parser.feed(xml, function (err, json) {
            var expected = {
                updates_count: 4,
                updates:       [
                    {
                        category: 'A State of Trance',
                        title:    'Armin van Buuren - A State of Trance 646 (2014-01-02) (RT)',
                        date:     '2014-01-03T00:46:10.000Z'
                    },
                    {
                        category: 'BBC Radio 1 Shows',
                        title:    'Friction - Drum & Bass Show (2013-29-12) (Best Of 2013) [TMB]',
                        date:     '2014-01-03T00:37:15.000Z'
                    },
                    {
                        category: 'A State of Trance',
                        title:    'Armin van Buuren - A State of Trance 646 (2014-01-02) [MM] (SBD)',
                        date:     '2014-01-02T22:51:19.000Z'
                    },
                    {
                        category: 'A State of Trance',
                        title:    'Armin van Buuren - A State of Trance 646 (2014-01-02) [Inspiron]',
                        date:     '2014-01-02T22:13:42.000Z'
                    }
                ]
            };

            if (err) {
                assert.ifError(err);
            }

            assert.deepEqual(json, expected);

            done();
        });
    });
});
