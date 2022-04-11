cd Authentication-Service
REM .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-auth-service
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-auth-service
docker push zedd08/soacil-auth-service

cd ..\Notification-Service
REM .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-notif-service
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-notif-service
docker push zedd08/soacil-notif-service

cd ..\Post-Service
REM .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-post-service
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-post-service
docker push zedd08/soacil-post-service

cd ..\User-Service
REM .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-user-service
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-user-service
docker push zedd08/soacil-user-service

cd ..