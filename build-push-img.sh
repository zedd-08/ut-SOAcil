cd Authentication-Service
# ./gradlew bootBuildImage --imageName=zedd08/soacil-auth-service
./gradlew clean
./gradlew bootJar
docker build . -t zedd08/soacil-auth-service
docker push zedd08/soacil-auth-service

cd ../Notification-Service
# ./gradlew bootBuildImage --imageName=zedd08/soacil-notif-service
./gradlew clean
./gradlew bootJar
docker build . -t zedd08/soacil-notif-service
docker push zedd08/soacil-notif-service

cd ../Post-Service
# ./gradlew bootBuildImage --imageName=zedd08/soacil-post-service
./gradlew clean
./gradlew bootJar
docker build . -t zedd08/soacil-post-service
docker push zedd08/soacil-post-service

cd ../User-Service
# ./gradlew bootBuildImage --imageName=zedd08/soacil-user-service
./gradlew clean
./gradlew bootJar
docker build . -t zedd08/soacil-user-service
docker push zedd08/soacil-user-service

cd ..