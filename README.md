<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<br />

<!-- PROJECT LOGO -->
<div align="center">
  <img src="images/big-data-logo.png" alt="Logo" width="450" height="250">
  <h3 align="center">VIEW#BIGDATA</h3>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
This project addresses the problems in the visualizations of the extended 3V model of Big Data.
The objective is the definition of a an interactive system for visualizing Big Data managed through a graph NoSQL database model.
This prototype allows the user to interactively visualize the contents of the graph database and focus on each node and it's associated links, while inspecting the node.

### Built With
* ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
* ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
* ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
* ![Neo4J](https://img.shields.io/badge/Neo4j-008CC1?style=for-the-badge&logo=neo4j&logoColor=white)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

In order to run this project. You will need to run the different components that are required to run the project successfully. The project is composed of 3 main components:
* 1 - Frontend, responsible for the User Interface.

* 2 - Backend, responsible for interacting with the database and it provides REST API to be consumed by the frontend or even other external applications

* 3 - Neo4J, a NoSql graph based database on which data is stored

The steps to run each components has been specified below.

### Prerequisites
<strong>NEO4J Database</strong> and <strong>DOCKER</strong>

Below are the steps to run neo4j using docker. If you want to run neo4j without docker you can refer to the instructions <a href="https://neo4j.com/docs/operations-manual/current/installation/">here</a>.
Please follow the steps in installation to create and run the neo4j docker container.

### Installation
<strong>NEO4J Database</strong> 

* 1 - For the first time, make sure that you comment the NEO4J_AUTH environment variable for the neo4j service in the docker-compose.yml file by adding # .

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

* 2 - Run the following command:

```shell
docker compose up -d
```

Note: if you face docker compose command not found error in the terminal, add the dash symbol between the docker and compose. This usually happens based on the OS. Don't worry if you get an error related to the springboot app at this stage since neo4j configuration is not complete yet.

```shell
docker-compose up -d
```

<br/>
* 3 - By default, the username and password is neo4j. You will be required to change it by accessing the Neo4J browser 
interface <a href="http://localhost:7474/browser/">here</a> . Log in by enter the default username and password and then set your new password.
<br/>

Note: The Neo4J browser URL is the default url that is running in your machine. Make sure that you change the URL for the Neo4J interface if you are running it on a different port number.

* 4 - Stop docker container by using the following command:

```shell
docker compose down
```
<br/>
* 5 - Uncomment the NEO4J_AUTH environment variable for the neo4j service in the docker-compose.yml file and put the new password you have set earlier by replacing your_new_password with the new password:

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
<br />
<br />

<strong>The backend</strong> 

-Without docker-

* 1 - Make sure that NEO4J is configured correctly.
<br/>

* 2 - In order to run the backend, first switch to this directory "/app/backend". If you are 
currently in the root folder of this repository. You can insert the following command:

```shell
cd ./app/backend
```

* 3 - Specify the environment variable by changing the values in /app/backend/src/main/resources/application.properties file. If application.properties file doesn't exist, copy the content of application.properties-sample and create a new application.properties file in the same folder. Customize the values according of the variables according to your configuration. 
<br/>


* 4 - Run the application using:
<br/>

<strong>MACOS/Linux</strong> 

```shell
./gradlew bootRun
```
Note: Make sure that gradlew file has executable permission. If it is not executable, update the file permission.
<br/>

<strong>Windows</strong> 

```shell
.\gradlew.bat bootRun
```

-With docker-

* 1 - Update the environment variable values by changing the values in docker-compose.yml file in the root directory of the project.  
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


* 2 - Run the application using:
<br/>

```shell
docker compose up -d
```

Note: If you have run the above command earlier when setting up neo4j. Stop the containers and run the above command again. To stop the containers, use the following command:

```shell
docker compose down
```
<br />
<br />

<strong> The frontend </strong>
<br />
-Without docker-

* 1 - Make sure that NEO4J is configured correctly.
<br/>

* 2 - In order to run the frontend, first switch to this directory "/app/frontend/graph-viewer". If you are 
currently in the root folder of this repository. You can insert the following command:

```shell
cd ./app/frontend/graph-viewer
```

* 3 - Install dependencies using

```shell
npm install
```

<br/>

* 4 - Run the application (make sure that the backend is already running first)

```shell
npm start
```

-With docker-

* 1 - if you have deployed the backend in a production enviroment or somewhere else you can specify the url of the backend by changing REACT_APP_BACKEND_URL enviroment variable in the docker-compose.yml file   
<br/>

```shell
frontend:
    build:
      context: ./app/frontend/graph-viewer 
      dockerfile: Dockerfile 
    networks:
      - docker-network
    container_name: frontend
    ports:
      - "4000:80"
    environment:
      - REACT_APP_BACKEND_URL=http://backend:8080
    depends_on:
      - backend 
```


* 2 - Run the application using:
<br/>

```shell
docker compose up -d
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage
After running the backend successfully, you can access an Interactive UI API documentation using the <a href="http://localhost:8080/swagger-ui/index.html">swagger user interface</a>

We are currently using three datasets, for trial you can choose any of datasets mentioned below, download each sheet in CSV format:

* 1 - Student/Course <a href="https://docs.google.com/spreadsheets/d/1H5moQQ0p5ozEcdqwO1rOs1SZWgghvgSCdZxwJV0DmKk/edit?usp=sharing">dataset</a>.
Import these sheets in the following order: Person -> Subject -> Enrollment -> Friendship.


* 2 - Digital twin <a href="https://docs.google.com/spreadsheets/d/1h1h-0PfRsZoJ7ZNNFhZl_Z1-fpmYzrqtHf_pj20sfvI/edit#gid=0">dataset</a>
Import these sheets in the following order: Capabilites -> Capabiliy-relationships

* 3 - Marvel <a href="https://docs.google.com/spreadsheets/d/154vkqvr3Adh98no4obw5KkVGJiXSBYK1/edit#gid=841937374">dataset</a>
Import the sheets in the following order: nodes —> edges —> hero-network

You can use the <a href="http://localhost:7474/browser/">NEO4j interface</a>  or the /nodes endpoint in the <a href="http://localhost:8080/swagger-ui/index.html">API Documentation</a>.
In order to import data, <strong>/csv endpoint</strong> can be used, it accepts two parameters: the csv file and the json string that contains the configuration.
<br/>
Use the config provided <a href="https://docs.google.com/document/d/16pGVM3PQ6YqANRYGSwljpZfaA9TJKV3-jmefUI0ySw8/edit?usp=sharing">here</a> for the config parameter.

<p align="right">(<a href="#readme-top">back to top</a>)</p>
