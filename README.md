# Running this project

In order to run this project. You will need to run the different components that are required to run the project successfully. The project is composed of 3 components:
<br/>

1- Frontend: The component that is responsible for the UI.
<br/>

2- Backend: The component that is responsible for interacting with the database and it provides REST API to be consumed by the frontend or even other external applications
<br/>

3- Neo4J: It is a NoSql graph based database on which data is stored
<br/>


The steps to run each components has been specified below:
<br/>

### NEO4J Database

Follow the following to create and run neo4j docker container:

1- For the first time, make sure that you comment the NEO4J_AUTH environment variable for the neo4j service in the docker-compose.yml file by adding # .

```shell
neo4j:
    image: neo4j:5.16.0
    container_name: neo4j
    restart: always
    environment:
      #NEO4J_AUTH: neo4j/some_random_password
    ports:
      - 7474:7474
      - 7687:7687
    volumes:
      - neo4j_db_data:/data
```
<br/>
2- Run the following command:

```shell
docker-compose up
```

Note: if you face docker compose command not found error in the terminal, add the dash symbol between the docker and compose. This usually happens based on the OS. 

```shell
docker-compose up
```

<br/>
3- By default, the username and password is neo4j. You will be required to change it by accessing the 

http://localhost:7474/browser/ . Log in by enter the default username and password and then set your new password.
<br/>


4- Stop docker container by using the following command:

```shell
docker compose down
```
<br/>
5- Uncomment the NEO4J_AUTH environment variable for the neo4j service in the docker-compose.yml file and put the new password you have set earlier by replacing your_new_password with the new password:

```shell
neo4j:
    image: neo4j:5.16.0
    container_name: neo4j
    restart: always
    environment:
      NEO4J_AUTH: neo4j/your_new_password
    ports:
      - 7474:7474
      - 7687:7687
    volumes:
      - neo4j_db_data:/data
```

<br/>

### The Backend

1- Make sure that NEO4J is configured correctly.
<br/>

2- In order to run the backend, first switch to this directory "/app/backend". If you are 
currently in the root folder of this repository. You can insert the following command:

```shell
cd ./app/backend
```

3- Specify the environment variable values by changing the values in /app/backend/src/main/resources/application.properties file.
<br/>


4- Run the application using:
<br/>

<strong>MACOS/Linux:</strong> 

```shell
./gradlew bootRun
```
Note: Make sure that gradlew file has executable permission. If it is not executable, update the file permission.
<br/>

<strong>Windows:</strong> 

```shell
.\gradlew.bat bootRun
```
