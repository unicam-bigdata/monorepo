# Running this project
In order to run this project. You will need to run the different components that are required to run the project successfully. The project is composed of 3 components:
<br/>
1- Frontend: The component that is responsible for the UI.
<br/>
2- Backend: The component that is responsible for interacting with the database and it provides REST API to be consumed by the frontend or even other external applications
<br/>
3- Neo4J: It is a NoSql graph based database on which data is stored
<br/>
<br/>
The steps to run each components has been specified below:
<br/>
## The Backend
<br/>
1- In order to run the backend first switch to this directory "/app/backend".
If you are currently in the root folder of this repository. You can insert the following command:
```
cd ./app/backend
```
<br/>
2- Specify the environment variable values by changing the values in /app/backend/src/resources/application.properties file.
<br/>
3- RUN the application using:
<br/>
<strong>MACOS/Linux:</strong> 
```
./gradlew bootRun
```
<br/>
<strong>Windows:</strong> 
```
.\gradlew.bat bootRun
```
