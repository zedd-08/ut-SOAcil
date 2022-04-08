# User Service

This is a synchronous service, that handles user related tasks. As it would potentially access user information, many of the endpoint methods perform a synchronous communication with the authentication service. It also sends an asynchronous notification when a user adds another user as a friend.

## Data models

The endpoints in this service make use of the following data models:

| Model        | Class                                 | Database                |
| ------------ | ------------------------------------- | ----------------------- |
| User         | `com.ut.user.models.User`             | `users`, `user_friends` |
| Notification | `com.ut.user.models.NotificationBody` | ---                     |
| Auth0        | `com.ut.user.models.Auth0Body`        | ---                     |

_**Note**: The `Auth0Body` and `Notification` models don't connect directly to any table in the database. They are used for synchronous communication with the authentication service, and asynchronous communication with the notification service._

## Endpoints

This service is hosted on `/v1/user-service`

The following endpoints are available:

1. **GET** on `/all`: This endpoint is open to all, for dummy implementation. It returns the list of all users currently on the application.

   - Doesn't require any inputs.
   - Returns a JSON payload as follows (Array of `User` model, returns passwords also, but we are not worried about security for this project 😉):

     ```
     [
     	{
     		"user_id": 2,
     		"user_handle": "username",
     		"firstName": "Name",
     		"lastName": "LastName",
     		"password": "password123",
     		"bio": "Just another user on SOAcil 😭",
     		"friends": [3, 7],
     		"joined": "2022-04-08T23:05:06.005Z"
     	},
     	...
     ]
     ```

2. **GET** on `/{userId}`: This endpoint is open to all, for dummy implementation. It returns the details of the requested user from the path variable.

   - Requires the `{userId}` path variable.
   - Returns a JSON payload as follows (`User` model, returns passwords also, but we are not worried about security for this project 😉):

     ```
     {
     	"user_id": 2,
     	"user_handle": "username",
     	"firstName": "Name",
     	"lastName": "LastName",
     	"password": "password123",
     	"bio": "Just another user on SOAcil 😭",
     	"friends": [3, 7],
     	"joined": "2022-04-08T23:05:06.005Z"
     }
     ```

3. **GET** on `/{userId}/friends`: This endpoint requires authentication, thus performs a synchronous call with the authentication service.

   - Requires the following header attributes:

     | key          | param                     |
     | ------------ | ------------------------- |
     | _user_id_    | 5                         |
     | _auth_token_ | Dd76NE8w73nsdKIdwoamd98JN |

   - Requires the `{userId}` path variable.

   - Returns a JSON payload containing array of `User` who are friends with the requested user; as follows (Array of `User` model):

     ```
     [
     	{
     		"user_id": 2,
     		"user_handle": "username",
     		"firstName": "Name",
     		"lastName": "LastName",
     		"password": "password123",
     		"bio": "Just another user on SOAcil 😭",
     		"friends": [3, 7],
     		"joined": "2022-04-08T23:05:06.005Z"
     	},
     	...
     ]
     ```

4. **POST** on `/create`: This endpoint would be accessed by the signup page of the application website. On creation of a new user, the user is automatically logged into the application performing a synchronous communication right after the user is created.

   - Requires a JSON payload as follows (`User` model):

     ```
     {
     	"user_handle": "username",
     	"firstName": "Name",
     	"lastName": "LastName",
     	"password": "password123",
     	"bio": "Just another user 😌"
     }
     ```

   - Returns a JSON payload as follows (`Auth0` model):

     ```
     {
     	"user_id": 2,
     	"auth_token": "Dd76NE8w73nsdKIdwoamd98JN",
     	"created_at": "2022-04-08T23:05:06.005Z"
     }
     ```

5. **PUT** on `/{userId}/add`: This endpoint is used to add another user as a friend. As it requires authentiaction, it performs a synchronous call to the authentication service. This service also sends a notification to the user added as friend, sending an asynchronous call to the notification service.

   - Requires the following header attributes:

     | key          | param                     |
     | ------------ | ------------------------- |
     | _user_id_    | 5                         |
     | _auth_token_ | Dd76NE8w73nsdKIdwoamd98JN |

   - Requires the `{userId}` path variable.
   - Returns a simple boolean payload, `true` if successfully added, `false` otherwise.
