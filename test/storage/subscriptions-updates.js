var assert = require("assert");

describe('storage: subscriptions-updates', function () {
    "use strict";

    var filename = __dirname + '/../../data/test.json',
        StorageSubscriptionsUpdates = require('../../src/storage/subscriptions-updates'),
        storageSubscriptionsUpdates = new StorageSubscriptionsUpdates(filename),
        StorageAbstract = require('../../src/storage/abstract');

    it('instance of abstract storage', function () {
        assert(storageSubscriptionsUpdates instanceof StorageAbstract);
    });

});
