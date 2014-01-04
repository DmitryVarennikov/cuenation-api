var assert = require("assert"),
    fs = require('fs');

describe('parser: HTML2JSON', function () {
    "use strict";

    var parser = new (require('../../src/parser/HTML2JSON'));

    it('categoriesPage', function () {
        var filename = __dirname + '/../../dev/categories.html',
            html = fs.readFileSync(filename, {flag: 'r', encoding: 'utf8'});

        parser.categoriesPage(html, function (err, json) {
            var expected;

            if (err) {
                assert(err);
            }

            expected = { subscriptions: [
                { image:      'http://cuenation.com/thumb.php?image=images/asos/asos.jpg&scale=33&qual=95',
                    category: 'http://cuenation.com/?page=cues&folder=asos',
                    desc:     'with Armin van Buuren' },
                { image:      'http://cuenation.com/thumb.php?image=images/asot/asot.jpg&scale=33&qual=95',
                    category: 'http://cuenation.com/?page=cues&folder=asot',
                    desc:     'with Armin van Buuren' },
                { image:      'http://cuenation.com/thumb.php?image=images/asotinvasion/asotinvasion.jpg&scale=33&qual=95',
                    category: 'http://cuenation.com/?page=cues&folder=asotinvasion',
                    desc:     'with Armin van Buuren' },
                { image:      'http://cuenation.com/thumb.php?image=images/asotpodcast/asotpodcast.png&scale=33&qual=95',
                    category: 'http://cuenation.com/?page=cues&folder=asotpodcast',
                    desc:     'with Armin van Buuren' },
                { category: 'http://cuenation.com/?page=cues&folder=aboraworld' }
            ] };

            assert.deepEqual(json, expected);
        });
    });
});
