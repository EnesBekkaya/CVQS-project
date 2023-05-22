
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

### Gateway

The gateway service in a Spring Boot microservices architecture acts as a centralized entry point for client requests. It provides a unified interface for clients to interact with multiple microservices. The gateway service handles request routing, service discovery, load balancing, and security, abstracting the complexities of the underlying microservices architecture. It enables efficient communication between clients and the backend microservices, enhancing scalability, performance, and security in the system.

```yml
spring:
  cloud:
    gateway:
      routes:
       - id: defect-listing-service
          uri: lb://DEFECT-LISTING-SERVICE
          predicates:
            - Path=/defectListing/**
          filters:
            - AuthenticationFilter=teamLeader
   application:
    name: gateway
server:
  port: 8888
```
### Eureka

Eureka is a service discovery and registration solution provided as part of Spring Cloud. It enables dynamic discovery and registration of services in microservices architectures.

With Eureka, services can register themselves and discover other services, allowing for a flexible and scalable architecture. It provides a central registry where services can register their availability and clients can discover and consume these services dynamically.

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}
```
### Security Service
In this project, I implemented a service that generates JWT (JSON Web Token) in Spring Security. The purpose of this service is to handle authentication and provide secure token-based authorization in this application.

When a user successfully authenticates the service generates a JWT containing encoded user information and any additional claims, such as roles or permissions. This JWT serves as a digitally signed token that can be used for subsequent authenticated requests.

```java
 public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        extraClaims.put("roles",roles);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
```
## Let's try it out

#### Before you start
- Install Docker and Docker Compose.

#### How to run
All images will be pulled from Docker Hub.

Copy the docker-compose.yml file and run the 'docker-compose up' command in the command prompt.


#### Important endpoints
- http://localhost:8761 - Eureka Dashboard
- http://localhost:8888 - Gateway 
