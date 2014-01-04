var assert = require("assert");

describe('storage: subscriptions', function () {
    "use strict";

    var filename = __dirname + '/../../data/test.json',
        StorageSubscriptions = require('../../src/storage/subscriptions'),
        storageSubscriptions = new StorageSubscriptions(filename),
        StorageAbstract = require('../../src/storage/abstract');

    it('instance of abstract storage', function () {
        assert(storageSubscriptions instanceof StorageAbstract);
    });

});
