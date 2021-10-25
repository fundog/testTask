# Common
This service was written in accordance with requirement from the test task. Hope it will match your expectations. Current application using Mysql DB. All properties for database connection you can find in `application.properties` file. Also was added `customProperties.properties` file to provide additional configuration possibilies as allowed email domains and number of bulk threads.

# Installation and Run

Application can be run in docker container (corresponding Dockerfile already filled and contains in a common folder). Also it can be started as usual Spring boot application.

# Structure
I will describe here only specific folders

### Configuration folder
Contains Statuses enum and WebConfig(to configure automatic transformation from xml to DTO object)
### Dto folder
Contains Data Transfer Object which match required structure of input XML document

# Test
Some part of functionality were covered by tests to increase development speed and avoid checking errors on fly.

# Resource fetch logic
For getting input XML we should use "/import" URL.
Steps:

- Get XML and transform it to DTO object
- Create resource with "/import" url (it means that we get it via our API)
- Setup for that resource status `COMPLETED` and add emails from XML emails part
- Create additional resources for each resource from XML and setup for them `WAITING_SENT`status. (it means that they will be process by scheduled job)

Job workflow (starts every 5 minutes):

- Get resources with status `WAITING_SENT` or `ERROR` from DB. (number of resources specified in `customProperties.properties` file, `numberOfBulkThreads` property)
- Eeach resource will be started in separate thread and perform request to required resourceUrl
- after successful completion for resource will be setted `COMPLETED` status and added emails. If some error appear we setup `ERROR` and this resource will be send in next time.
- For new resources from XML will created new resource entites.

