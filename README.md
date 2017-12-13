# Project Sleepless Backend (Spring) Document

## 0. Documentaion Record

| Document Version | Time                | Description                              |
| ---------------- | ------------------- | ---------------------------------------- |
| v0.1.0           | 2017/12/14 02:31:00 | First edition released for the use of presentaion |



## 1. Environment Establishment

### 1.1. Dependencies

1. Maven
2. MongoDB

### 1.2. Run the Project

1. Run in terminal,

   ```
   > mongod
   ```

   - This step aims to get the mongodb running, which is essential for our services to work.

2. CD to the root directory of the spring project,

   ```
   > cd XXX/Project
   Project>
   ```

3. Run in terminal,

   ```
   Project> mvn clean package
   ```

   - You may need to export bin directory of Maven to PATH in order to make this happen.

4. Start registry application in a terminal,

   ```
   <Terminal 1>
   Project> java -jar registry/target/registry-0.1.0.jar
   ```

5. Start reverse-proxy application in a new terminal,

   ```
   <Terminal 2>
   Project> java -jar reverse-proxy/target/reverse-proxy-0.1.0.jar
   ```

6. Start services, each in a new terminal, in any order you like, for instance,

   ```
   <Terminal 3>
   Project> java -jar user-service/target/user-service-0.1.0.jar

   <Terminal 4>
   Project> java -jar user-info-service/target/user-info-service-0.1.0.jar

   <Terminal 5>
   Project> java -jar task-service/target/task-service-0.1.0.jar
   ```

7. Two extra services canonization-service0 and canonization-service1 are not necessary but can be used as a test method to check if the services can actually work,

   ```
   <Extra Terminal 1>
   Project> java -jar canonization-service0/target/canonization-service0-0.1.0.jar

   <Extra Terminal 2>
   Project> java -jar canonization-service1/target/canonization-service1-0.1.0.jar
   ```

- Note that config-server is not used at this time.

### 1.3. Notes

1. It takes time to start each of the applications. But be sure that registry application has already been started before any other service or application begins to start.
2. Even after a service is started, its apis may not work because its connection to mongodb is not yet set. Under this circumstances, usually, you just have to wait seconds (or perhaps around 1 minute) for it to connect. If it still doesn't work, try use the apis and wait another few seconds.



## 2. API Document

### 2.1. User Service

1. POST http://localhost:8089/user/createUser/v1 with JSON file using application/json as content-type in headers. The format of JSON file should be as follows,

   ```
   {
   	"email" : "nonsense@qq.com",
   	"password" : "fuckyou",
   	"name" : "cascader",
   	"gender" : "OTHER",
   	"phoneNumber" : "15666666666",
   	"address" : "nowhere",
   	"website" : "www.nowhere.com",
   	"job" : "useless",
   	"avatar" : "www.nowhere.com/cascader.jpg"
   }
   ```

   Where the requirement for parameters is listed below,

   | Parameter   | Required | Type                                     |
   | ----------- | -------- | ---------------------------------------- |
   | email       | true     | String                                   |
   | password    | true     | String                                   |
   | name        | true     | String                                   |
   | gender      | true     | enum, either "MALE", "FEMALE", or "OTHER" |
   | phoneNumber | true     | String                                   |
   | address     |          | String                                   |
   | website     |          | String                                   |
   | job         |          | String                                   |
   | avatar      |          | String                                   |

   A possible returned JSON file could be,

   ```
   {
       "status": 200,
       "message": "User Created",
       "data": {
           "user": {
               "id": 10001,
               "email": "nonsense@qq.com",
               "tasks": [
                   1000001
               ]
           },
           "userInfo": {
               "id": 10001,
               "name": "cascader",
               "gender": "OTHER",
               "address": "nowhere",
               "website": "www.nowhere.com",
               "phoneNumber": "15666666666",
               "job": "useless",
               "avatar": "www.nowhere.com/cascader.jpg"
           }
       }
   }
   ```

2. GET http://localhost:8089/user/deleteUser/test

   - This GET call is used for testing, which will remove all the data in the database upon calling.
   - The data removed are of type User and type UserInfo, and don't include type Task as well as Subtask for now.

### 2.2. Task service

1. GET http://localhost:8089/task/getTask/{id}

   - This GET call is used with {id} replaced by an actual integer representing the id of a task.
   - The id can be acquired with User service, contained by tasks array, where each element means an id of a task.
   - The first task id ever acquired upon creating a user is the task id of tutorial task.

   An example for call

   ```
   http://localhost:8089/task/getTask/1000000
   ```

   can be

   ```
   {
       "status": 200,
       "message": "Request Accepted",
       "data": {
           "id": 1000000,
           "name": "Tutorial",
           "content": "This is a tutorial task for newcomers to get used to the system.",
           "ownerId": 10000,
           "taskState": "INPROCESS",
           "priority": "HIGH",
           "date": 1513189585200,
           "users": [
               10000
           ],
           "subtasks": [
               1000000,
               1000001
           ]
       }
   }
   ```

   Where subtasks array contains ids of subtasks, which we now won't need to introduce.

2. GET http://localhost:8089/task/deleteTask/test

   - This GET call is used for testing, which will remove all the data in the database upon calling.
   - The data removed are of type Task and Subtask.



# NOTIFICATION

- **Since the services are very immature, any inconsistency spotted or new requirements arisen should be reported to nemoremold for further implementation.**

