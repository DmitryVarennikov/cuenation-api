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
{
	"subscriptions": [
    	{
        	"category": "<string>",
            "desc":     "<string>",
            "image":    "<string>"
        },
        // ...
    ]
}
```

### Subscriptions updates
**Request**
```
POST /subscriptions-updates.json
categories[]=<string>&categories[]=<string>&last-update=<YYYY-MM-DDTHH:II:SS>
```
**Response**
```
{
	"updates": [
    	{
        	"category": "<string>",
            "title":    "<string>",
            "date":     "<YYYY-MM-DDTHH:II:SS>"
        },
        // ...
    ]
}
```