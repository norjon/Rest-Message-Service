# Rest-Message-Service
Rest Message Service

This is a service where messages can be send to a receiver preferably identified by a phone number. Messages for that phone number can be retrieved and they will then be flagged as read. It is possible to retrieve all messages and to retrieve a number of messages based on indices. See below for examples of how to use the rest service.

There is Docker Build file which can be used to build a docker image by using the command: ./mvnw spring-boot:build-image

By running this in docker with Docker Swarm it is possible to guarantee resilience by having at least one replica running at all times. However right now it is setup to use an H2 instance for dev purposes. By using a clustered DB solution it would be possible to use the same source in order to guarantee data integrity and redundancy.

# Initialize with some test data
curl -v http://localhost:8080/inittestdata

# Retrieve message for phone number and limited to start and stop indices from a list sorted by timestamp
curl -v http://localhost:8080/readsomemessages?phonenumber=0701234567&start=0&stop=0

# Retrieve all messages
curl -v http://localhost:8080/readallmessages

# Delete messages according to criteria in posted object
curl -X POST http://localhost:8080/deletemessages -H "Content-Type:application/json" -d '{"timestamp":"1603579368999"}'

# Send message
curl -X POST http://localhost:8080/sendmessage -H "Content-Type:application/json" -d '{"message":"1","phoneNumber":"124"}'


