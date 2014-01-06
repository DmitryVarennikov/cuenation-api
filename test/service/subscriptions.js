var assert = require("assert");

describe('service: subscriptions', function () {
    "use strict";

    function invokeService(storage, parser) {
        return new (require('../../src/service/subscriptions'))(storage, parser);
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
                categoriesPage: function (html, callback) {
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
                categoriesPage: function (html, callback) {
                    callback(null, html);
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

    it('get: error', function (done) {
        var expected = 'error',
            storage = {
                'get': function (callback) {
                    callback(expected);
                }
            },
            parser = {},
            service = invokeService(storage, parser);

        service.get(function (err, data) {
            assert.strictEqual(err, expected);

            done();
        });
    });

    it('get: success', function (done) {
        var expected = {a: 1},
            storage = {
                'get': function (callback) {
                    callback(null, expected);
                }
            },
            parser = {},
            service = invokeService(storage, parser);

        service.get(function (err, data) {
            assert.deepEqual(data, {subscriptions: expected});

            done();
        });
    });
});
