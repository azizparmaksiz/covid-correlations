## Covid Correlation Coefficient

This rest service return correlation coefficient of death and vaccinated count.

The covid-19 related deaths and vaccinated counts data are fetched from [COVID API](https://github.com/M-Media-Group/Covid-19-API)

### Setup
This application use;
* Java 17 version
* Redis for Caching.

calling [COVID API](https://github.com/M-Media-Group/Covid-19-API) frequently may result banning your ip.
For this reason I use caching to keep data on local. 

To run application you will need [Redis](https://redis.io/) server installed or has access to any redis server.

You can also use Redis as a [Docker](https://www.docker.com/get-started/) container.

If you have docker installed on your machine, you can run command `docker-compose up` on project root folder to run
Redis instance as a docker container.

### Run
After application run you can access the [REST API](http://localhost:9090/swagger-ui.html#/covid-correlation-controller) over swagger
