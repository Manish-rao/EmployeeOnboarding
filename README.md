# EmployeeOnboarding

## Prerequisite
Installed:
* Docker
* git
* Java 1.8 or 11.1
* Maven 3.x

# Steps
## Clone source code from git
` git clone https://github.com/Manish-rao/EmployeeOnboarding . `

## Maven package:
` mvn package`

## Build Docker image
` docker build --build-arg JAR_FILE=target/*.jar -t empapp . `

## Run Docker Container:
`  docker run -p 8080:8080 -it --rm empapp  `

## Verfication:
` curl http://localhost:8081//swagger-ui-custom.html `

# Endpoints:
## AddEmployee:
```
Request Type: POST
http://localhost:8081/addEmployee

Sample Json Body:
{
    "email":"2@123.com",
    "userName": "Test",
    "phoneNumber": 9139393333,
    "address":"Test Address",
    "age":null
}
```

## UpdateEmployee:
```
Request Type: POST
http://localhost:8081/updateEmployee?newState=IN_CHECK&email=2@123.com

newState: parameter which denotes next state 
email: parameter which contains a pre-existing email id
```

## Swagger UI:
http://localhost:8081//swagger-ui-custom.html

# Assumptions:

## States: ADDED -> IN-CHECK -> APPROVED -> ACTIVE

- Employee email id will be considered as the unique key since most registration pages have email as username. Hence this field has a validation check for uniqueness. If same email id is enetered, BAD request will be the response. Else employee record gets created with ADDED state.
- newState and email will be sent as parameter. If newState is not in the states above or is not the valid next state we will get an exception. If the email id is not valid we will receive an exception

# Improvements:

-  Stress testing was not considered due to time constraints. 
-  Authentication could be considered, one for employee and nother for admin.
-  Would love to go through the documentation for Spring State machine and understand the flow better. 




