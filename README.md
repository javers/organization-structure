# organization-structure
Sample application demonstrating use of JaVers Spring Boot starters.

## spring-boot-starter-data-mongodb

To start app execute:
 
```
./gradlew organization-structure-mongo:bootRun
```

Application requires MongoDB (3.0.0+) running on port 27017.

## REST API

Application exposes REST interface:

```
http://localhost:8080/view/hierarchy
http://localhost:8080/view/hierarchy/Hier_2015
http://localhost:8080/view/person
http://localhost:8080/view/person/0
```

```
Create New Person
Post http://localhost:8080/view/person
{
	"id": 101,
	"firstName": "Yang",
	"lastName": "Huajie",
	"sex": "MALE",
	"salary": 22,
	"position": "DEVELOPER"
}
```

## spring-boot-starter-data-sql

To start app execute:
 
```
./gradlew organization-structure-sql:bootRun
```
