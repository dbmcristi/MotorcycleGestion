GET http://localhost:8084/participant/all

GET http://localhost:8084/race/all

GET http://localhost:8084/race/search?engineSize=CM_125

DELETE http://localhost:8084/race/5

POST http://localhost:8084/race/
{
"name": "Raliu",
"engineCapacity": "CM_500"
}

PUT http://localhost:8084/race/
{
"id": 1,
"name": "MEOW",
"engineCapacity": "CM_500"
}

