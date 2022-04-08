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