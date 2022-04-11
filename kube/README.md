# Kubernetes deployment cluster

This application uses [`minikube`](https://minikube.sigs.k8s.io/docs/start/) for a minimal kubernetes installation. The deployments were tested using Docker desktop on Windows 11 with WSL2 backend.

This folder contains all the required deployment YAML files to ge tthe application up and running.

## YAML description

### MYSQL

There are two files required for mysql deployment to k8s.

1. The file `mysql-pv.yaml` contains persistent storage ivolume information used by mysql pod for storage. This is neccessary because it would prevent loss of information if an existing pod stops running.
2. The file `mysql-deployment.yaml` contains the deployment information for the `mysql:5.6` image to the k8s cluster. The following environment variables are required to setup the DB for use with the application:

   ```
   - name: "MYSQL_ROOT_PASSWORD"
     value: "password"
   - name: "MYSQL_USER"
     value: "soauser"
   - name: "MYSQL_PASSWORD"
     value: "soauser"
   - name: "MYSQL_DATABASE"
     value: "soadb"
   - name: "MYSQL_ROOT_HOST"
     value: "%"
   ```

   This file also exposes the default mysql port `3306` and runs the service with name `mysql`. This makes the mysql db accessible using the JDBC connection string `jdbc:mysql://mysql:3306/soadb`, as can be seen in `application.properties of each of the service`

These options are in accordance with the docker image run command required:

```
docker run --name soacil-mysql \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=password \
-e MYSQL_USER=soauser \
-e MYSQL_PASSWORD=soauser \
-e MYSQL_DATABASE=soadb \
-d mysql:latest
```

### RabbitMQ

RabbitMQ doesn't require extra deployment steps or variables, except for exposing the default port `5672`. This is also in accordance with the docker image run command for rabbitmq:

```
docker run -d -p 5672:5672 --hostname my-rabbit --name soacil-rabbit rabbitmq:latest
```

## Deployment steps

1. Install `minikube` and run `minikube start` to start the k8s cluster.
2. Navigate to this directory
3. Run
   ```
   kubectl apply -f mysql
   ```
   This will create the volume, deploy mysql and start the `mysql` service
4. Run
   ```
   kubectl apply -f rabbit
   ```
   This will deploy and start the service for rabbitMQ.
5. Once both the k8s services are up, run
   ```
   kubectl apply -f app-deployment.yaml
   ```
   This will deploy the images of the four services of SOAcil and start the `soacilapp` service.
6. Once all k8s services are deployed, run
   ```
   kubectl tunnel
   ```
   This will expose the `soacilapp` service, which is a `LoadBalancer` service, on the mentioned ports - `9090` for _auth-service_, `9091` for _notif-service_, `9092` for _post-service_ and `9093` for _user-service_. This will allow us to access the application from our machine.

We can monitor realtime status using `kubectl dashboard` which opens the dashboard UI in a new browser window.

Kubernetes automatically pulls the required images from docker hub, thus it is not neccessary to pull the images in advance.
