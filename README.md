CueNation API
=============
[![Build Status](https://travis-ci.org/dVaffection/cuenation-api.png?branch=master)](https://travis-ci.org/dVaffection/cuenation-api)

Provides API ends for [CueNation Chrome ext](https://github.com/dVaffection/cuentation-chrome-ext)

## Spec

### Subscriptions
**Request**
```
GET /subscriptions.json
```
**Response**
```
// error response
{
	"status": false,
    "err":    "<string>"
}
// success response
{
	"status": true,
    "data": {
    	"subscriptions": [
            {
                "category": "<string>",
                "desc":     "<string>",
                "image":    "<string>"
            },
            // ...
        ]
    }
}
```

### Subscriptions updates
**Request**
```
GET /subscriptions-updates.json?categories[]=<string>&categories[]=<string>&last-update=<ISO_8601>
```
**Response**
```
// error response
{
	"status": false,
    "err":    "<string>"
}
// success response
{
	"status": true,
    "data": {
    	"updates_count":    "<integer>",
        "updates": [
            {
                "category": "<string>",
                "title":    "<string>",
                // http://en.wikipedia.org/wiki/ISO_8601
                "date":     "<ISO_8601>"
            },
            // ...
        ]
    }
}
```
