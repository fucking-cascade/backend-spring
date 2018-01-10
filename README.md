# Project Sleepless Backend (Spring) Document

## 0. Documentaion Revisions

| Document Version | Time                | Description                              |
| ---------------- | ------------------- | ---------------------------------------- |
| v0.1.0           | 2017/12/14 02:31:00 | First edition released for the use of presentaion |
| v0.2.0           | 2018/01/10 14:42    | Second edition released for the use of final presentaion |

## 1. Environment Establishment

### 1.1. Dependencies

1. Maven
2. RabbitMQ

### 1.2. Run the Project

1. Run in terminal,

   ```
   <Terminal 1>
   > rabbitmq-server
   ```

   - This step aims to get the mongodb running, which is essential for our services to work.

2. CD to the root directory of the spring project,

   ```
   > cd XXX/Project
   Project>
   ```

3. Run in terminal,

   ```
   Project> mvn clean install package
   ```


4. Start eureka application in a terminal,

   ```
   <Terminal 2>
   Project/eureka> mvn spring-boot:run

   //or use java -jar target/eureka-0.2.0.snapshot.jar
   ```

5. Start config, zuul turbine application in new terminals,

   ```
   <Terminal 3>
   Project/config> mvn spring-boot:run

   //or use java -jar target/config-0.2.0.snapshot.jar
   ```

   ```
   <Terminal 4>
   Project/zuul> mvn spring-boot:run

   //or use java -jar target/zuul-0.2.0.snapshot.jar
   ```

   ```
   <Terminal 5>
   Project/turbine> mvn spring-boot:run

   //or use java -jar target/turbine-0.2.0.snapshot.jar
   ```

6. Start services, each in a new terminal, in any order you like, preferably having relation service run in the first place before using any other service's apis,

   ```
   <Terminal x>
   Project/service-xx/core> mvn spring-boot:run

   //or use java -jar target/service-xx-core-0.2.0.snapshot.jar
   ```

### 1.3. Notes

1. It takes time to start each of the applications. But be sure that eureka application has already been started before any other service or application begins to start.
2. Even after a service is started, its apis (those mapped to zuul) may not work because its connection to zuul is not yet set. Under this circumstances, usually, you just have to wait seconds (or perhaps around 1 minute) for it to connect. If it still doesn't work, try use the apis and wait another few seconds.



## 2. API Document

### 2.0. NOTES

#### 2.0.1. **FOR POST REQUESTS**

- Post **JSON** file 
- Use **application/json** as content-type in headers

#### 2.0.2. **All REQUESTS RETURN JSON FILE**

- Returned JSON file has two types

  - SUCCESS

    - Where status would be 200, for instance

      ```json
      {
          "timestamp": 1515571827097,
          "status": 200,
          "message": {
              "userId": "20",
              "email": "int@latheild.org"
          }
      }
      ```

  - FAILED

    - Where status would not be 200, for instance,

      ```json
      {
          "timestamp": 1515573621645,
          "status": 401,
          "message": "Unauthorized, Wrong password",
          "error": "WRONG_PASSWORD",
          "exception": "org.latheild.apiutils.exception.AppBusinessException"
      }
      ```

### 2.1. *User Service*

#### 2.1.1. Register a user

- **POST** [localhost:12306/user/register

- The format of JSON file should be as follows,

  ```json
  {
  	"email": "int@latheild.org",
  	"password": "integrationtesting",
  	"name": "Yifei BAI",
  	"gender": 1,
  	"phoneNumber": "15666666669",
  	"address": "No.4800 Caoay, To",
  	"website": "org.latheild.intg",
  	"job": "fucking project member",
  	"avatar": "http://org.lat",
  	"birthday": "1996-08-08"
  }
  ```


- Where the requirement for parameters is listed below,

  | Parameter   | Required | Type    |
  | ----------- | -------- | ------- |
  | email       | true     | String  |
  | password    | true     | String  |
  | name        | true     | String  |
  | gender      | true     | Boolean |
  | phoneNumber | true     | String  |
  | address     | true     | String  |
  | website     | true     | String  |
  | job         | true     | String  |
  | avatar      | true     | String  |
  | birthday    | true     | String  |


- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515571589917,
      "status": 200,
      "message": {
          "userId": "20",
          "email": "int@latheild.org",
          "name": "Yifei BAI",
          "gender": true,
          "phoneNumber": "15666666669",
          "address": "No.4800 Caoay, To",
          "website": "org.latheild.intg",
          "job": "fucking project member",
          "avatar": "http://org.lat",
          "birthday": "1996-08-08"
      }
  }
  ```

#### 2.1.2. Get a user

- **GET** [localhost:12306/user/retrieve]()

- Where the requirement for parameters is listed below,

  | Parameter | Required                      | Type   |
  | --------- | ----------------------------- | ------ |
  | id        | either id or email must exist | String |
  | email     | either id or email must exist | String |


- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515571827097,
      "status": 200,
      "message": {
          "userId": "20",
          "email": "int@latheild.org"
      }
  }
  ```

#### 2.1.3. Get all users

- **GET** [localhost:12306/user/retrieve/all]()

#### 2.1.4. Check user password

- **POST** [localhost:12306/user/password/check]()

- The format of JSON file should be as follows,

   ```json
   {
   	"userId": "21",
   	"email": "int2@latheild.org",
   	"password": "integrationtesting"
   }
   ```


- Where the requirement for parameters is listed below,

  | Parameter | Required                          | Type   |
  | --------- | --------------------------------- | ------ |
  | userId    | either userId or email must exist | String |
  | email     | either userId or email must exist | String |
  | password  | true                              | String |


- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515573327241,
      "status": 200,
      "message": true
  }
  ```

#### 2.1.5. Reset user password

- **POST** [localhost:12306/user/password/reset]()

- The format of JSON file should be as follows,

  ```json
  {
  	"userId": "21",
  	"oldPassword": "wrongPassword",
  	"newPassword": "newPassword"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter   | Required | Type   |
  | ----------- | -------- | ------ |
  | userId      | true     | String |
  | oldPassword | true     | String |
  | newPassword | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515573621645,
      "status": 401,
      "message": "Unauthorized, Wrong password",
      "error": "WRONG_PASSWORD",
      "exception": "org.latheild.apiutils.exception.AppBusinessException"
  }
  ```

### 2.2. *UserInfo service*

#### 2.2.1. Get a user's info

- **GET** [localhost:12306/userinfo/retrieve]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | userId    | true     | String |


- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515574164785,
      "status": 200,
      "message": {
          "name": "Yifei BAI",
          "gender": true,
          "address": "No.4800 Caoay, To",
          "website": "org.latheild.intg",
          "phoneNumber": "15666666669",
          "job": "fucking project member",
          "avatar": "http://org.lat",
          "userId": "21",
          "birthday": "1996-08-08T00:00:00.000Z"
      }
  }
  ```

#### 2.2.2. Get all user infos

- **GET** [localhost:12306/userinfo/retrieve/all]()

- Where the requirement for parameters is listed below,

  | Parameter | Required                                 | Type   |
  | --------- | ---------------------------------------- | ------ |
  | name      | false, if exists, returns list of user infos with the same name | String |


- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515574349714,
      "status": 200,
      "message": [
          {
              "name": "Yifei BAI",
              "gender": true,
              "address": "No.4800 Caoay, To",
              "website": "org.latheild.intg",
              "phoneNumber": "15666666669",
              "job": "fucking project member",
              "avatar": "http://org.lat",
              "userId": "20",
              "birthday": "1996-08-08T00:00:00.000Z"
          },
          {
              "name": "Yifei BAI",
              "gender": true,
              "address": "No.4800 Caoay, To",
              "website": "org.latheild.intg",
              "phoneNumber": "15666666669",
              "job": "fucking project member",
              "avatar": "http://org.lat",
              "userId": "21",
              "birthday": "1996-08-08T00:00:00.000Z"
          }
      ]
  }
  ```

#### 2.2.3. Update user info

- **POST** [localhost:12306/user/info/update]()

- The format of JSON file should be as follows,

  ```json
  {
  	"name": "Yifei LV",
  	"gender": true,
  	"address": "No.4800 Cao",
  	"website": "org.latheild.www",
  	"phoneNumber": "15666666669",
  	"job": "fucking project member",
  	"avatar": "http://org.eee",
  	"userId": "20",
  	"birthday": "1996-08-08T00:00:00.000Z"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter   | Required | Type   |
  | ----------- | -------- | ------ |
  | userId      | true     | String |
  | name        | true     | String |
  | address     | true     | String |
  | website     | true     | String |
  | phoneNumber | true     | String |
  | job         | true     | String |
  | avatar      | true     | String |
  | birthday    | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515574543890,
      "status": 200,
      "message": {
          "name": "Yifei LV",
          "gender": true,
          "address": "No.4800 Cao",
          "website": "org.latheild.www",
          "phoneNumber": "15666666669",
          "job": "fucking project member",
          "avatar": "http://org.eee",
          "userId": "20",
          "birthday": "1996-08-08T00:00:00.000Z"
      }
  }
  ```

### 2.3. *Project service*

#### 2.3.1. User creates a project

- **POST** [localhost:12306/project/create]()

- The format of JSON file should be as follows,

  ```json
  {
  	"ownerId": "20",
  	"name": "Test",
  	"description": "This is a test"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter   | Required | Type   |
  | ----------- | -------- | ------ |
  | ownerId     | true     | String |
  | name        | true     | String |
  | description | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515575654291,
      "status": 200,
      "message": {
          "projectId": "42",
          "ownerId": "20",
          "name": "Test",
          "description": "This is a test"
      }
  }
  ```

#### 2.3.2. Get a project by project id

- **GET** [localhost:12306/project/retrieve]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | id        | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515575654291,
      "status": 200,
      "message": {
          "projectId": "42",
          "ownerId": "20",
          "name": "Test",
          "description": "This is a test"
      }
  }
  ```

#### 2.3.3. Get all projects owned by a user

- **GET** [localhost:12306/project/retrieve/all]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | ownerId   | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515575983345,
      "status": 200,
      "message": [
          {
              "projectId": "40",
              "ownerId": "20",
              "name": "Tutorial p01",
              "description": "This is a tutorial"
          },
          {
              "projectId": "42",
              "ownerId": "20",
              "name": "Test",
              "description": "This is a test"
          }
      ]
  }
  ```

#### 2.3.4. Project owner removes a project

- **POST** [localhost:12306/project/delete]()

- The format of JSON file should be as follows,

  ```json
  {
  	"ownerId": "20",
  	"projectId": "42"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | ownerId   | true     | String |
  | projectId | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515576086280,
      "status": 200,
      "message": "Success request"
  }
  ```

#### 2.3.5. Project owner updates project info

- **POST** [localhost:12306/project/update/info]()

- The format of JSON file should be as follows,

  ```json
  {
  	"ownerId": "20",
  	"projectId": "40",
  	"name": "hahaha",
  	"description": "changeddescription"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter   | Required | Type   |
  | ----------- | -------- | ------ |
  | ownerId     | true     | String |
  | projectId   | true     | String |
  | name        | true     | String |
  | description | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515576241977,
      "status": 200,
      "message": {
          "projectId": "40",
          "ownerId": "20",
          "name": "hahaha",
          "description": "changeddescription"
      }
  }
  ```

#### 2.3.6. Project owner transfers ownership

- **POST** [localhost:12306/project/update/owner]()

- The format of JSON file should be as follows,

  ```json
  {
  	"oldOwnerId": "20",
  	"projectId": "40",
  	"newOwnerId": "22"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter  | Required | Type   |
  | ---------- | -------- | ------ |
  | projectId  | true     | String |
  | oldOwnerId | true     | String |
  | newOwnerId | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515576802652,
      "status": 200,
      "message": {
          "projectId": "40",
          "ownerId": "22",
          "name": "hahaha",
          "description": "changeddescription"
      }
  }
  ```

### 2.4. *Progress service*

#### 2.4.1. User creates a progress

- **POST** [localhost:12306/progress]()

- The format of JSON file should be as follows,

  ```json

  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

#### 2.4.2. Get a progress by progress id

- **GET** [localhost:12306/progress/retrieve]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | id        | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515578844419,
      "status": 200,
      "message": {
          "progressId": "7",
          "ownerId": "20",
          "name": "Tutorial prepare",
          "projectId": "40",
          "order": 0
      }
  }
  ```

#### 2.4.3. Get all progress

- **GET** [localhost:12306/progress/retrieve/all]()

- Where the requirement for parameters is listed below,

  | Parameter | Required                                 | Type   |
  | --------- | ---------------------------------------- | ------ |
  | ownerId   | either ownerId or projectId must be present, if both, returns the progress owned by a user in a project | String |
  | projectId | either ownerId or projectId must be present, if both, returns the progress owned by a user in a project | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515578938140,
      "status": 200,
      "message": [
          {
              "progressId": "7",
              "ownerId": "20",
              "name": "Tutorial prepare",
              "projectId": "40",
              "order": 0
          },
          {
              "progressId": "8",
              "ownerId": "20",
              "name": "Tutorial ongoing",
              "projectId": "40",
              "order": 1
          },
          {
              "progressId": "9",
              "ownerId": "20",
              "name": "Tutorial done",
              "projectId": "40",
              "order": 2
          }
      ]
  }
  ```

#### 2.4.4. Progress owner removes a progress

- **POST** [localhost:12306/progress/delete]()

- The format of JSON file should be as follows,

  ```json
  {
  	"progressId": "7",
  	"ownerId": "20"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter  | Required | Type   |
  | ---------- | -------- | ------ |
  | progressId | true     | String |
  | ownerId    | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515579155693,
      "status": 200,
      "message": "Success request"
  }
  ```

#### 2.4.5. Progress owner changes progress name

- **POST** [localhost:12306/progress/update/name]()

- The format of JSON file should be as follows,

  ```json
  {
  	"progressId": "8",
  	"ownerId": "20",
  	"name": "TutorialCHanged"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter  | Required | Type   |
  | ---------- | -------- | ------ |
  | progressId | true     | String |
  | ownerId    | true     | String |
  | name       | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515579232891,
      "status": 200,
      "message": {
          "progressId": "8",
          "ownerId": "20",
          "name": "TutorialCHanged",
          "projectId": "40",
          "order": 0
      }
  }
  ```

#### 2.4.6. Progress owner changes progress order

- **POST** [localhost:12306/progress/update/order]()

- The format of JSON file should be as follows,

  ```json
  {
  	"progressId": "8",
  	"ownerId": "20",
  	"order": 1
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter  | Required | Type    |
  | ---------- | -------- | ------- |
  | progressId | true     | String  |
  | ownerId    | true     | String  |
  | order      | true     | Integer |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515580612467,
      "status": 200,
      "message": {
          "progressId": "8",
          "ownerId": "20",
          "name": "TutorialCHanged",
          "projectId": "40",
          "order": 1
      }
  }
  ```

### 2.5. *Task service*

#### 2.5.1. User creates a task

- **POST** [localhost:12306/task]()

- The format of JSON file should be as follows,

  ```json
  {
  	"ownerId": "20",
  	"progressId": "8",
  	"name": "newTASK",
  	"content": "This is a new task",
  	"state": false,
  	"ddl": "1996-08-08T00:00:00.000Z"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter  | Required | Type    |
  | ---------- | -------- | ------- |
  | ownerId    | true     | String  |
  | progressId | true     | String  |
  | name       | true     | String  |
  | content    | true     | String  |
  | state      | true     | Boolean |
  | ddl        | true     | String  |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515581374871,
      "status": 200,
      "message": {
          "taskId": "46",
          "ownerId": "20",
          "progressId": "8",
          "name": "newTASK",
          "content": "This is a new task",
          "state": false,
          "ddl": "1996-08-08T00:00:00.000Z"
      }
  }
  ```

#### 2.5.2. Get a task by task id

- **GET** [localhost:12306/task/retrieve]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | id        | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515581074257,
      "status": 200,
      "message": {
          "taskId": "22",
          "ownerId": "20",
          "progressId": "8",
          "name": "Tutori0001",
          "content": "This is a tutorial task",
          "state": false,
          "ddl": "1996-08-08T00:00:00.000Z"
      }
  }
  ```

#### 2.5.3. Get all tasks

- **GET** [localhost:12306/task/retrieve/all]()

- Where the requirement for parameters is listed below,

  | Parameter  | Required                                 | Type   |
  | ---------- | ---------------------------------------- | ------ |
  | ownerId    | either ownerId or progressId must be present, if both, returns the tasks owned by a user under a progress | String |
  | progressId | either ownerId or progressId must be present, if both, returns the tasks owned by a user under a progress | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515581022571,
      "status": 200,
      "message": [
          {
              "taskId": "22",
              "ownerId": "20",
              "progressId": "8",
              "name": "Tutori0001",
              "content": "This is a tutorial task",
              "state": false,
              "ddl": "1996-08-08T00:00:00.000Z"
          },
          {
              "taskId": "23",
              "ownerId": "20",
              "progressId": "8",
              "name": "Tu0002",
              "content": "This is a tutorial task",
              "state": false,
              "ddl": "1996-08-08T00:00:00.000Z"
          },
          {
              "taskId": "24",
              "ownerId": "20",
              "progressId": "8",
              "name": "Tutor0003",
              "content": "This is a tutorial task",
              "state": false,
              "ddl": "1996-08-08T00:00:00.000Z"
          }
      ]
  }
  ```

#### 2.5.4. Task owner removes a task

- **POST** [localhost:12306/task]()

- The format of JSON file should be as follows,

  ```json
  {
  	"taskId": "46",
  	"ownerId": "20"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | taskId    | true     | String |
  | ownerId   | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515581488867,
      "status": 200,
      "message": "Success request"
  }
  ```

#### 2.5.5. Task owner updates info of a task

- **POST** [localhost:12306/task]()

- The format of JSON file should be as follows,

  ```json
  {
  	"taskId": "22",
  	"ownerId": "20",
  	"name": "newTASK",
  	"content": "This is a new task",
  	"ddl": "1996-08-08T00:00:00.000Z"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | taskId    | true     | String |
  | ownerId   | true     | String |
  | name      | true     | String |
  | content   | true     | String |
  | ddl       | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515581614905,
      "status": 200,
      "message": {
          "taskId": "22",
          "ownerId": "20",
          "progressId": "8",
          "name": "updatedTask",
          "content": "This is a updated task",
          "state": false,
          "ddl": "1996-08-08T00:00:00.000Z"
      }
  }
  ```

#### 2.5.6. Task owner changes state of a task

- **POST** [localhost:12306/task]()

- The format of JSON file should be as follows,

  ```json
  {
  	"taskId": "22",
  	"ownerId": "20",
  	"state": true
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type    |
  | --------- | -------- | ------- |
  | taskId    | true     | String  |
  | ownerId   | true     | String  |
  | state     | true     | Boolean |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515581753373,
      "status": 200,
      "message": {
          "taskId": "22",
          "ownerId": "20",
          "progressId": "8",
          "name": "updatedTask",
          "content": "This is a updated task2",
          "state": true,
          "ddl": "1996-08-08T00:00:00.000Z"
      }
  }
  ```

### 2.6. *Subtask service*

#### 2.6.1. User creates a subtask

- **POST** [localhost:12306/subtask]()

- The format of JSON file should be as follows,

  ```json
  {
  	"userId": "20",
  	"taskId": "23",
  	"content": "Tutorial LANYU",
  	"state": false
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type    |
  | --------- | -------- | ------- |
  | userId    | true     | String  |
  | taskId    | true     | String  |
  | content   | true     | String  |
  | state     | true     | Boolean |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515582257574,
      "status": 200,
      "message": {
          "subtaskId": "109",
          "userId": "20",
          "taskId": "23",
          "content": "Tutorial LANYU",
          "state": false
      }
  }
  ```

#### 2.6.2. Get a subtask by subtask id

- **GET** [localhost:12306/subtask]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | id        | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515582074456,
      "status": 200,
      "message": {
          "subtaskId": "37",
          "userId": "20",
          "taskId": "22",
          "content": "Tutorial LANYU",
          "state": false
      }
  }
  ```

#### 2.6.3. Get all subtasks

- **GET** [localhost:12306/subtask/all]()

- Where the requirement for parameters is listed below,

  | Parameter | Required                                 | Type   |
  | --------- | ---------------------------------------- | ------ |
  | userId    | either userId or taskId must be present, if both, returns the subtasks owned by a user under a task | String |
  | taskId    | either userId or taskId must be present, if both, returns the subtasks owned by a user under a task | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515582130364,
      "status": 200,
      "message": [
          {
              "subtaskId": "40",
              "userId": "20",
              "taskId": "23",
              "content": "Tutorial LANYU",
              "state": false
          },
          {
              "subtaskId": "41",
              "userId": "20",
              "taskId": "23",
              "content": "Tutorial subLANYU",
              "state": false
          },
          {
              "subtaskId": "42",
              "userId": "20",
              "taskId": "23",
              "content": "TutoriLANYU",
              "state": false
          }
      ]
  }
  ```

#### 2.6.4. Subtask owner removes a subtask

- **POST** [localhost:12306/subtask]()

- The format of JSON file should be as follows,

  ```json
  {
  	"subtaskId": "109",
  	"userId": "20"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | subtaskId | true     | String |
  | userId    | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515582597830,
      "status": 200,
      "message": "Success request"
  }
  ```

#### 2.6.5. Subtask owner updates subtask content

- **POST** [localhost:12306/subtask]()

- The format of JSON file should be as follows,

  ```json
  {
  	"subtaskId": "109",
  	"userId": "20",
  	"content": "changed content"
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type   |
  | --------- | -------- | ------ |
  | subtaskId | true     | String |
  | userId    | true     | String |
  | content   | true     | String |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515582415619,
      "status": 200,
      "message": {
          "subtaskId": "109",
          "userId": "20",
          "taskId": "23",
          "content": "changed content",
          "state": false
      }
  }
  ```

#### 2.6.6. Subtask owner changes subtask state

- **POST** [localhost:12306/subtask]()

- The format of JSON file should be as follows,

  ```json
  {
  	"subtaskId": "109",
  	"userId": "20",
  	"state": true
  }
  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type    |
  | --------- | -------- | ------- |
  | subtaskId | true     | String  |
  | userId    | true     | String  |
  | state     | true     | Boolean |

- A possible returned JSON file could be,

  ```json
  {
      "timestamp": 1515582507193,
      "status": 200,
      "message": {
          "subtaskId": "109",
          "userId": "20",
          "taskId": "23",
          "content": "changed content",
          "state": true
      }
  }
  ```

### 2.7. Schedule service

#### 2.7.1.

- **POST** [localhost:12306/]()

- The format of JSON file should be as follows,

  ```json

  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

#### 2.7.2.

- **GET** [localhost:12306/]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

### 2.8. Comment service

#### 2.8.1.

- **POST** [localhost:12306/]()

- The format of JSON file should be as follows,

  ```json

  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

#### 2.8.2.

- **GET** [localhost:12306/]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

### 2.9. File service

#### 2.9.1.

- **POST** [localhost:12306/]()

- The format of JSON file should be as follows,

  ```json

  ```

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

#### 2.9.2.

- **GET** [localhost:12306/]()

- Where the requirement for parameters is listed below,

  | Parameter | Required | Type |
  | --------- | -------- | ---- |
  |           |          |      |

- A possible returned JSON file could be,

  ```json

  ```

### 2.10. Relation service

## 3. Configuration Document

### 3.1. Ports

```
Eureka:             10086
	Turbine:            12345
	Zuul:               12306
	Config:             12312
		(configuration center: https://github.com/fucking-cascade/backend-config)
        user-service:       10000
		user-info-service:  10001
		subtask-service:    10002
		comment-service:    10003
		project-service:    10004
		progress-service:   10005
		task-service:       10006
		schedule-service:   10007
		file-service:       10008
		relation-service:   10009
```


# NOTIFICATION

- **Any inconsistency spotted or new requirements arisen should be reported to nemoremold for further implementation.**

