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
```
{
   "_links":{
      "self":{
         "href":"http://localhost:8080/user-tokens/a20f45eb-5edc-44d4-a91a-7fa3554a091b"
      }
   },
   "id":"53e133d8e4b0171f2ed38bc0",
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
