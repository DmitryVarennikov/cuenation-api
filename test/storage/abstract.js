var assert = require("assert"),
    fs = require('fs');

describe('storage: abstract', function () {
    "use strict";

    var filename = __dirname + '/../../data/test.json',
        storage = new (require('../../src/storage/abstract'))(filename);

    beforeEach(function () {
        if (fs.existsSync(filename)) {
            fs.unlinkSync(filename);
        }
    });

    afterEach(function () {
        if (fs.existsSync(filename)) {
            fs.unlinkSync(filename);
        }
    });

    it('get: storage does not exist', function (done) {
        storage.get(function (err, data) {
            assert.strictEqual(err.id, 'storageDoesNotExist');

            done();
        });
    });

    it('get: success', function (done) {
        var data = {a: 1},
            rawData = JSON.stringify(data);

        fs.writeFileSync(filename, rawData, {flag: 'w', encoding: 'utf8'});

        storage.get(function (err, actual) {
            if (err) {
                assert.ifError(err);
            }

            assert.deepEqual(actual, data);

            done();
        });
    });

    it('set: success', function (done) {
        var data = {a: 1},
            rawData,
            actual;

        storage.set(data, function (err) {
            if (err) {
                assert.ifError(err);
            }

            rawData = fs.readFileSync(filename, {flag: 'r', encoding: 'utf8'});
            actual = JSON.parse(rawData);
            assert.deepEqual(actual, data);

            done();
        });
    });
});
