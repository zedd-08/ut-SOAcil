# SOA-cil

A social network application using SOA concepts, made as a project for the SOA course at the University of Twente - 2022.

This project aims at creating a social networking website using microservices. The project uses the following tools:

- Backend code written in **Java** using _Spring-boot_
- **ReactJS** for frontend code
- **MySQL** database for persistance storage
- **RabbitMQ** for asynchronous message queue
- **Swagger-UI** for documentation
- **Consul** for service discovery
- **\*Kubernetes** for deployment

The project uses REST services and works specifically using JSON payloads.

## Available Services

There are currently 4 backend services in the project, each one of them in their own folders. These services together make the application work.

- Authentication service
- Notification service
- Post service
- User service

More details on each service is present in the README in the services' respective folders.

## Data models and database connection

The following data models are used:

1. Auth0:
   ```
   {
   	"user_id" : 1,
   	"auth_token": "3jeuNE8w73nsdKIdwoamd98JN",
   	"created_at": "2022-04-08T21:59:38.306Z"
   }
   ```
2. Notification:
   ```
   {
   	"id": 4,
   	"user_id": 2,
   	"from_user_id":3,
   	"description":"This is notification 4 from user 3 to user 2",
   	"created_at":"2022-04-08T21:59:38.306Z"
   }
   ```
3. Post:
   ```
   {
   	"post_id": 4,
   	"content": "This is post 4 from user 2, with 5 likes 😁",
   	"user_id": 2,
   	"likes": 5,
   	"created_at": "2022-04-08T22:54:12.211Z"
   }
   ```
4. User:
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

We have used [MySQL](https://www.mysql.com/) database for persistant storage. The tables are automatically created using JPA and hibernate on the database with name `soaDB`. The following tables are used from the database:

| Table name      | Model representation             |
| --------------- | -------------------------------- |
| `authtoken`     | `Auth0`                          |
| `notifications` | `Notification`                   |
| `posts`         | `Post`                           |
| `users`         | `User`                           |
| `user_friends`  | _Stores array of user's friends_ |
