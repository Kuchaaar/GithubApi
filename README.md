# GitHub Repository List API
### Overview
This API provides a simple and user-friendly way for consumers to retrieve 
a list of GitHub repositories for a given user. 
### Endpoint
```GET``` ```/repositories```
#### Params
```username```
#### Headers
```Accept: application/json```
#### Response
The response will contain a JSON array with information about
each non-fork repository belonging to the specified GitHub user.
For example:
```json
[
  {
    "name": "example-name1",
    "login": "example-login",
    "branches": [
      {
        "name": "example-branch1",
        "lastCommitSha": "abc123"
      },
      {
        "name": "example-branch2",
        "lastCommitSha": "123abc"
      }
    ]
  },
  {
    "name": "example-name2",
    "login": "example-login",
    "branches": [
      {
        "name": "example-name",
        "lastCommitSha": "abc123"
      }
    ]
  }
]
```
If you give wrong username you will get error like example:
```json
{
    "status": 404,
    "message": "error-message"
}
```



### Requirements
In application.properties you should set your own GitHub
access token to get more request limits.
