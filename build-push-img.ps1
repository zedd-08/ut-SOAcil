Set-Location 'Authentication-Service'
# .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-auth-service
.\gradlew.bat clean
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-auth-service
docker push zedd08/soacil-auth-service

Set-Location '..\Notification-Service'
# .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-notif-service
.\gradlew.bat clean
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-notif-service
docker push zedd08/soacil-notif-service

Set-Location '..\Post-Service'
# .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-post-service
.\gradlew.bat clean
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-post-service
docker push zedd08/soacil-post-service

Set-Location '..\User-Service'
# .\gradlew.bat bootBuildImage --imageName=zedd08/soacil-user-service
.\gradlew.bat clean
.\gradlew.bat bootJar
docker build . -t zedd08/soacil-user-service
docker push zedd08/soacil-user-service

Set-Location '..'