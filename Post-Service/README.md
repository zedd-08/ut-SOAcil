# Posts Service

This is a synchronous service, that handles post related tasks. As it handles posts created by certain user and the users' friends, many of the endpoint methods perform a synchronous communication with the authentication service. 

This service also allows for adding of a new post for the user that is logged in. And it allows for the editing of the post as well as liking and deleting of the post. It also sends an asynchronous notification when another user likes your post.

## Data models

The endpoints in this service make use of the following data models:

| Model        | Class                                               | Database |
| ------------ | --------------------------------------------------- | -------- |
| Post         | `com.ut.soacil.postservice.models.Post`             | `posts`  |
| Notification | `com.ut.soacil.postservice.models.NotificationBody` | ---      |
| Auth0        | `com.ut.soacil.postservice.models.Auth0Body`        | ---      |

_**Note**: The `Auth0Body` and `Notification` models don't connect directly to any table in the database. They are used for synchronous communication with the authentication service, and asynchronous communication with the notification service._

## Endpoints

This service is hosted on `/v1/post-service`

The following endpoints are available:

1. **GET** on `/all`: This endpoint is open to all and would serve as landing page of the application website.

   - Doesn't require any inputs.
   - Returns a JSON payload as follows (Array of `Post` model):

     ```
     [
     	{
     		"post_id": 22,
     		"content": "This is a post on SOAcil!",
     		"user_id": 2,
     		"likes": 4,
     		"created_at": "2022-04-08T22:50:57.922Z"
     	},
     	...
     ]
     ```

Getting all posts:
![](img/post-all.png)

2. **GET** on `/{post_id}`: This endpoint is also open to all. It returns only a single post object.

   - Requires the `{post_id}` path variable.
   - Returns a JSON payload as follows (`Post` model):

     ```
     {
     	"post_id": 22,
     	"content": "This is a post on SOAcil!",
     	"user_id": 2,
     	"likes": 4,
     	"created_at": "2022-04-08T22:50:57.922Z"
     }
     ```

Getting a single post:
![](img/post-single.png)

3. **POST** on `/add`: This endpoint is used to create a new post by a user. It requires authentication and thus performs a synchronous communication with the authentication service.

   - Requires the following header attributes:

     | key          | param                     |
     | ------------ | ------------------------- |
     | _user_id_    | 5                         |
     | _auth_token_ | Dd76NE8w73nsdKIdwoamd98JN |

   - Requires a JSON payload as follows (`Post` model):

     ```
     {
     	"content": "This is a post on SOAcil!",
     	"user_id": 2
     }
     ```

   - Returns a JSON payload with more details about the post (`Post` model):

   ```
   	{
   		"post_id": 0,
   		"content": "string",
   		"user_id": 0,
   		"likes": 0,
   		"created_at": "2022-04-08T22:54:12.211Z"
   	}
   ```

Creating a new post:
![](img/post-add.png)

Trying to create a new post with invalid authentication:
![](img/post-add-unauthorized.png)

4. **PUT** on `/{post_id}/update`: This endpoint is used to update any existing post by a user. It requires authentication and thus performs a synchronous communication with the authentication service. **The post can only be updated by the post's original author.**

   - Requires the following header attributes:

     | key          | param                     |
     | ------------ | ------------------------- |
     | _user_id_    | 5                         |
     | _auth_token_ | Dd76NE8w73nsdKIdwoamd98JN |

   - Requires the `{post_id}` path variable.
   - Requires a JSON payload as follows (`Post` model):

     ```
     {
     	"content": "This is an updated post on SOAcil!",
     	"user_id": 2
     }
     ```

   - Returns a simple string payload with message "_Updated post_"

Updating an existing post:
![](img/post-updated.png)

5. **PUT** on `/{post_id}/like`: This endpoint is used to like any existing post by a user. It requires authentication and thus performs a synchronous communication with the authentication service.

   - Requires the following header attributes:

     | key          | param                     |
     | ------------ | ------------------------- |
     | _user_id_    | 5                         |
     | _auth_token_ | Dd76NE8w73nsdKIdwoamd98JN |

   - Requires the `{post_id}` path variable.

   - Returns a simple string payload with message "_Liked post_"

Liking a post, sends a notification to the author of the post:
![](img/post-like.png)

6. **DELETE** on `/{post_id}`: This endpoint is used to delete any existing post by a user. It requires authentication and thus performs a synchronous communication with the authentication service. **The post can only be deleted by the post's original author.**

   - Requires the following header attributes:

     | key          | param                     |
     | ------------ | ------------------------- |
     | _user_id_    | 5                         |
     | _auth_token_ | Dd76NE8w73nsdKIdwoamd98JN |

   - Requires the `{post_id}` path variable.

   - Returns a simple string payload with message "_Deleted post_"

Deleting a post:
![](img/post-deleted.png)
## Swagger UI

![Post service Swagger UI](Swagger-post-service.jpeg)
