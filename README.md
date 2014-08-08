CueNation API
=============

Provides API ends for [CueNation Chrome ext](https://github.com/dVaffection/cuentation-chrome-ext)

# API ends

### User tokens

**Request**
`GET /user-tokens/{token}`

**Success response headers**
```
HTTP/1.1 200 OK
Content-Type: application/hal+json
```
**Success response body**
```json
{
  "_links":{
    "self":{
      "href":"http://localhost:8080/user-tokens/a20f45eb-5edc-44d4-a91a-7fa3554a091b"
    }
  },
  "token":"a20f45eb-5edc-44d4-a91a-7fa3554a091b"
}
```
**Error response headers**
```
HTTP/1.1 404 Not Found
```
=============

**Request**
`POST /user-tokens`

**Success response headers**
```
HTTP/1.1 201 Created
Location: http://localhost:8080/user-tokens/3b394585-e84c-4df8-9aa0-9fd067ed66ce
```

### Cue categories

**Request**
`GET /cue-categories`

**Success response headers**
```
HTTP/1.1 200 OK
Content-Type: application/hal+json
```
**Success response body**
```json
[
  {
    "id":"53e3f55779261ba4e6388443",
    "name":"Uncontrolled Environment",
    "link":"http://cuenation.com/?page=cues&folder=uncontrolledenvironment"
  }
]
```

### User cue categories

**Request**
`GET /user-tokens/{token}/categories`

**Success response headers**
```
HTTP/1.1 200 OK
Content-Type: application/hal+json
```
**Success response body**
```json
[
  {
    "id":"53e3f55779261ba4e6388443",
    "name":"Uncontrolled Environment",
    "link":"http://cuenation.com/?page=cues&folder=uncontrolledenvironment"
  }
]
```

**Request headers**
```
Content-Type: application/json
```

**Request body**
```
PUT /user-tokens/{token}/categories
{
  "category_ids":[
    "53e3f55779261ba4e6388443",
    "53e3f55779261ba4e638844d"
  ]
}
```

**Success response headers**
```
HTTP/1.1 200 OK
Location: http://localhost:8080/user-tokens/{token}/categories
```