CueNation API
=============
Provides API calls for [CueNation Chrome ext](https://github.com/dVaffection/cuentation-chrome-ext)

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
GET /subscriptions-updates.json?categories[]=<string>&categories[]=<string>&last-update=<YYYY-MM-DDTHH:II:SS>
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
                "date":     "<YYYY-MM-DDTHH:II:SS>"
            },
            // ...
        ]
    }
}
```