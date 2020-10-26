# ANotes backup server
Backup server for ANotes app

## Features:
+ Backup/restore user notes
+ JWT Authentication

## Technologies:
+ Java/Vavr
+ Spring Boot
+ Hibernate
+ PostgreSQL 13
+ JWT

## Docker

### Deploy & run with docker-compose
+ `cd anotes/` # project root
+ `docker-compose up` # deploy app + db

### Clean run
+ `cd anotes/` # project root
+ `docker-compose stop` # stop app + db
+ `docker-compose rm` # remove app + db
+ `docker-compose up` # deploy new app + db

## API short notation

### Register a new user [POST `/register`]
+ Request (application/json;charset=UTF-8)

    + Headers
    + Body
    
            {
                "nickname": "someNick",
                "password": "somePasswd1",
                "fullName": "John Doe"
            }
+ Response 200 (application/json;charset=UTF-8)
   
    + Headers
    
            Authorization: Bearer sASsada_some_token
            
    + Body
    
+ Response 400 (application/json;charset=UTF-8)

    + Headers
    
    + Body
    
            {
                "timestamp": "2020-10-25T16:15:05.636+00:00",
                "status": 400,
                "errors": [
                    "Nickname 'someNick' is already taken"
                ]
            }

### Login [POST `/login`]
+ Request (application/json;charset=UTF-8)

    + Headers
    
    + Body
    
            {
                "nickname": "someNick",
                "password": "somePasswd1"
            }
            
+ Response 200 (application/json;charset=UTF-8)
   
    + Headers
    
            Authorization: Bearer sASsada_some_token
            
    + Body
        
### Backup user notes [POST `/api/backup`]
+ Request (application/json;charset=UTF-8)

    + Headers
    
            Authorization: Bearer sASsada_some_token
            
    + Body
    
            {
                "notes": [
                    {
                        "title": "Drink water",
                        "text": "All water\n",
                        "pinned": true
                    },
                    {
                        "title": "Learn how to code",
                        "text": "Or mayb note\n...",
                        "pinned": false,
                        "reminderDate": "2021-11-19T21:19:18"
                    }
                ]
            }
            
+ Response 200 (application/json;charset=UTF-8)
   
    + Headers
            
    + Body
            
            {
                "userId": 1,
                "snapshotMd5": "ab13928f0c3087317cb3a07f54fd692e",
                "creationDate": "2020-10-19T22:27:47.564798"
            }

### Restore last user backup [GET `/api/restore`]
+ Request (application/json;charset=UTF-8)

    + Headers
    
            Authorization: Bearer sASsada_some_token
            
    + Body
            
+ Response 200 (application/json;charset=UTF-8)
   
    + Headers
            
    + Body
            
            {
                "notes": [
                    {
                        "title": "Drink water",
                        "text": "All water\n",
                        "pinned": true,
                        "reminderDate": null
                    },
                    {
                        "title": "Learn how to code",
                        "text": "Or mayb note\n...",
                        "pinned": false,
                        "reminderDate": "2021-11-19T21:19:18"
                    }
                ]
            }
