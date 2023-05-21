
# CVQS

CVQS is a project built in accordance with the Microservices Architecture Model using Spring Boot, Spring Cloud, and Docker. It allows users to record, list, update and mark tool-based errors and terminals on given error photos based on the locations entered by the user.

<br>


## Functional services

Cvqs is decomposed into four core microservices.They are all separate applications that manage specific jobs.

<img width="880" alt="Functional services" src="https://user-images.githubusercontent.com/83051118/235381194-4b6cb09d-167a-4013-bbcb-9bc478dd09e8.jpg">

#### User Management service
User save, update and deletion operations.

Method	| Path	| Description	| User authenticated	|
------------- | ------------------------- | ------------- |:-------------:|
POST	| /users/save	| save new user	|  | 	
POST	| /users/update	| Update user data | × 
GET	| /users/getAll	| Get current user data	| × 
DELETE	| /users/delete	| Delete user	| × 
POST	| /roles/save	| save new role	|  | 	



#### Terminal service
Terminal and section saving and listing operations.

Method	| Path	| Description	| User authenticated	
------------- | ------------------------- | ------------- |:-------------:|
POST	| /terminals/save	| Save new terminal	| ×
GET	| /terminals/active	| Get active terminal         | × | 	
GET	| /terminals/page	| Get terminal by page 	| × 
GET	| /terminals/sort| Get terminals in order | ×
POST	| /sections/save| Sace new section | ×
GET	| /sections/getAll| Get all terminals | ×



#### Defect Save service
Defect save and updating operations.


Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
POST	| /defects/save	| Save new defect	| × 
POST	| /defects/update	| Update defect	| ×
POST	| /vehicle/save	| Save vehicle	| ×
GET	| /vehicle/getAll	| Get all vehicle	| ×

#### Defect Listing service
Defect and image listing operations.


Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
GET	| /defectListing/getAll	| Get all defect	| × 
GET	| /defectListing/getByPlate	| Get defect by plate| ×
GET	| /defectListing/getImage	| Get image 	| ×
GET	| /defectListing/sort	| Get defect in order	| ×

## Infrastructure
<img width="880" alt="Infrastructure services" src="https://user-images.githubusercontent.com/83051118/235382264-9276f10f-06db-4b37-aec3-e2e381edd893.jpg">

## Let's try it out

#### Before you start
- Install Docker and Docker Compose.

#### How to run
All images will be pulled from Docker Hub.

Copy the docker-compose.yml file and run the 'docker-compose up' command in the command prompt.


#### Important endpoints
- http://localhost:8761 - Eureka Dashboard
- http://localhost:8888 - Gateway 
