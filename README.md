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

Below are the steps to run neo4j using docker. If you want to run neo4j without docker you can refer to the instructions <a href="https://neo4j.com/docs/operations-manual/current/installation/">here</a>.Please follow the following steps to create and run neo4j docker container:

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
docker compose up -d
```

Note: if you face docker compose command not found error in the terminal, add the dash symbol between the docker and compose. This usually happens based on the OS. Don't worry if you get an error related to the springboot app at this stage since neo4j configuration is not complete yet.

```shell
docker-compose up -d
```

<br/>
3- By default, the username and password is neo4j. You will be required to change it by accessing the Neo4J browser 
interface <a href="http://localhost:7474/browser/">here</a> . Log in by enter the default username and password and then set your new password.
<br/>

Note: The Neo4J browser URL is the default url that is running in your machine. Make sure that you change the URL for the Neo4J interface if you are running it on a different port number.

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

#### Without docker

1- Make sure that NEO4J is configured correctly.
<br/>

2- In order to run the backend, first switch to this directory "/app/backend". If you are 
currently in the root folder of this repository. You can insert the following command:

```shell
cd ./app/backend
```

3- Specify the environment variable by changing the values in /app/backend/src/main/resources/application.properties file. If application.properties file doesn't exist, copy the content of application.properties-sample and create a new application.properties file in the same folder. Customize the values according of the variables according to your configuration. 
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

#### With docker

1- Update the environment variable values by changing the values in docker-compose.yml file in the root directory of the project.  
<br/>

```shell
springbootapp:
    build:
      context: ./app/backend 
      dockerfile: Dockerfile 
    ports:
      - "8080:8080"
    environment:
      - backend.neo4j.uri=bolt://IP:7687 
      - backend.neo4j.username=neo4j
      - backend.neo4j.password=DATABASE_PASSWORD
      - backend.public_domain=http://IP:8080/ # It is the IP or domain of the backend.
    depends_on:
      - neo4j 
```


2- Run the application using:
<br/>

```shell
docker compose up -d
```

Note: If you have run the above command earlier when setting up neo4j. Stop the containers and run the above command again. To stop the containers, use the following command:

```shell
docker compose down
```
<br/>

#### API documentation
After running the backend successfully, you can access an Interactive UI API documentation using this link: <a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a>

### Import data 
This application has been developed to visualize data with various data models (schemas). Unlike SQL databases, where the data schema should be modeled beforehand amd rigid, in this application, the data schema is flexible and changes as data is inserted or updated. This was possible because we have used a NO-SQL graph database called NEO4J.

<br/>

For demonstration, we used the dataset available <a href="https://docs.google.com/spreadsheets/d/1H5moQQ0p5ozEcdqwO1rOs1SZWgghvgSCdZxwJV0DmKk/edit?usp=sharing">here</a>. Download each sheet in CSV format.

<br/>

In order to import data, /csv endpoint can be used. This endpoint accepts two parameters: file: the csv file and config: json string that contains the configuration. Use the <a href="http://localhost:8080/swagger-ui/index.html">API Documentation</a> to import the data.

<br/>

The data set consists of list of students, courses, enrollment and friendship (which student is the best friend of the other). Use the config provided <a href="https://docs.google.com/document/d/16pGVM3PQ6YqANRYGSwljpZfaA9TJKV3-jmefUI0ySw8/edit?usp=sharing">here</a> for the config parameter.

<br/>

You can use the <a href="http://localhost:7474/browser/">NEO4j interface</a>  or the /nodes endpoint in the <a href="http://localhost:8080/swagger-ui/index.html">API Documentation</a>. Import the sheets in the following order: Person -> Subject -> Enrollment -> Friendship.