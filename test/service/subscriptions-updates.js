var assert = require("assert");

describe('service: subscriptions-updates', function () {
    "use strict";

    function invokeService(storage, parser) {
        return new (require('../../src/service/subscriptions-updates'))(storage, parser);
    }

    it('update: http error', function (done) {
        var expected = 'http error',
            storage = {},
            parser = {},
            service = invokeService(storage, parser);

        service._httpRequest = function (onRes, onErr) {
            onErr(expected);
        };

        service.update(function (err) {
            assert.strictEqual(err, expected);

            done();
        });
    });

    it('update: parser error', function (done) {
        var expected = 'parser error',
            parser = {
                feed: function (xml, callback) {
                    callback(expected);
                }
            },
            storage = {},
            service = invokeService(storage, parser);

        service._httpRequest = function (onRes, onErr) {
            onRes();
        };

        service.update(function (err) {
            assert.strictEqual(err, expected);

            done();
        });
    });

    it('update: success', function (done) {
        var expected = {a: 1},
            parser = {
                feed: function (xml, callback) {
                    callback(null, xml);
                }
            },
            storage = {
                'set': function (json, callback) {
                    assert.deepEqual(json, expected);

                    callback();
                }
            },
            service = invokeService(storage, parser);

        service._httpRequest = function (onRes, onErr) {
            onRes(expected);
        };

        service.update(function (err) {
            done();
        });
    });
});
