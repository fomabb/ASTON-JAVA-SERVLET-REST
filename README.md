# ASTON-SPRING-HIBERNATE-REST

# RESTful Web-application is written using the following technologies:

- SpringBoot
- FlyWay
- PostgresSQL DB

### Short description of the project

- Standalone application providing REST API

### Prerequisites:

- Java 17
- PostgresSQL

# Data model

## ER diagram for the data model

![db.png](src%2Fmain%2Fresources%2Fimg%2Fdb.png)

## RESTful API

**1. API Description of general methods for Course**

| METHOD | PATH                                                                | DESCRIPTION                    |
|--------|---------------------------------------------------------------------|--------------------------------|
| POST   | /api/courses                                                        | create new course              |
| POST   | /api/student/add/studentId/{studentId}/to/courseId/{courseId}       | add student to course          |
| PUT    | /api/courses?id={courseId}                                          | add teacher to course          |
| PUT    | /api/course/studentId/{studentId}/to/teacherId/{teacherId}          | add student to teacher         |
| PUT    | /api/course/delete-from-teacherId/{teacherId}/studentId/{studentId} | delete student from teacher    |
| PUT    | /api/course/{id}                                                    | update course name             |
| GET    | /api/course                                                         | get all courses                |
| GET    | /api/course/{id}                                                    | get course by ID               |
| GET    | /api/course/{courseId}/with-students                                | get course by id with students |
| GET    | /api/course/{courseId}/teachers                                     | get course by id with teachers |
| DELETE | /api/course/{id}                                                    | delete course by id            |

___POST: Request body for method create new course___

```json
{
  "name": "Aston"
}
```

```json
{
  "id": 1,
  "name": "Aston"
}
```

___GET: get course by id with teachers___

```json
{
  "id": 1,
  "name": "Aston",
  "teachers": [
    {
      "id": 2,
      "name": "Vladimir",
      "status": "TEACHER",
      "students": [
        {
          "id": 3,
          "name": "Max",
          "status": "STUDENT"
        },
        {
          "id": 4,
          "name": "Piter",
          "status": "STUDENT"
        },
        {
          "id": 8,
          "name": "Alina",
          "status": "STUDENT"
        },
        {
          "id": 9,
          "name": "Veronika",
          "status": "STUDENT"
        }
      ]
    },
    {
      "id": 10,
      "name": "Pavel",
      "status": "TEACHER",
      "students": [
        {
          "id": 5,
          "name": "Jek",
          "status": "STUDENT"
        },
        {
          "id": 6,
          "name": "Jhon",
          "status": "STUDENT"
        },
        {
          "id": 7,
          "name": "Olga",
          "status": "STUDENT"
        }
      ]
    }
  ]
}
```

___GET: get all student by course id___

```json
{
  "id": 1,
  "name": "Aston",
  "students": [
    {
      "id": 3,
      "name": "Max",
      "status": "STUDENT"
    },
    {
      "id": 4,
      "name": "Piter",
      "status": "STUDENT"
    },
    {
      "id": 5,
      "name": "Jek",
      "status": "STUDENT"
    },
    {
      "id": 6,
      "name": "Jhon",
      "status": "STUDENT"
    },
    {
      "id": 7,
      "name": "Olga",
      "status": "STUDENT"
    },
    {
      "id": 8,
      "name": "Alina",
      "status": "STUDENT"
    },
    {
      "id": 9,
      "name": "Veronika",
      "status": "STUDENT"
    }
  ]
}
```

**2. API Description of general methods for Teacher**

| METHOD | PATH                            | DESCRIPTION                     |
|--------|---------------------------------|---------------------------------|
| POST   | /api/teacher                    | create new teacher              |
| GET    | /api/teacher                    | get all teachers                |
| GET    | /api/teachers/{id}              | get teacher by id               |
| GET    | /api/teacher/{id}/with-students | get teacher by id with students |
| DELETE | /api/course/{id}                | delete teacher by id            |


___POST: Request body for method create new teacher___

```json
{
  "name": "Vladimir"
}
```

___Response___

```json
{
  "id": 2,
  "name": "Vladimir",
  "status": "TEACHER"
}
```

___GET: get teacher by id with students___

```json
{
  "id": 2,
  "name": "Vladimir",
  "status": "TEACHER",
  "students": [
    {
      "id": 3,
      "name": "Max",
      "status": "STUDENT"
    },
    {
      "id": 4,
      "name": "Piter",
      "status": "STUDENT"
    },
    {
      "id": 8,
      "name": "Alina",
      "status": "STUDENT"
    },
    {
      "id": 9,
      "name": "Veronika",
      "status": "STUDENT"
    }
  ]
}
```

**3. API Description of general methods for Student**

| METHOD | PATH               | DESCRIPTION          |
|--------|--------------------|----------------------|
| POST   | /api/student       | create new student   |
| GET    | /api/student       | get all students     |
| GET    | /api/student/{id}  | get student by id    |
| DELETE | /api/students/{id} | delete student by id |

___POST: Request body for method create new student___

```json
{
  "name": "Max"
}
```

___Response___

```json
{
  "id": 3,
  "name": "Max",
  "status": "STUDENT"
}
```

___GET: get all students___

```json
[
  {
    "id": 3,
    "name": "Max",
    "status": "STUDENT"
  },
  {
    "id": 4,
    "name": "Piter",
    "status": "STUDENT"
  },
  {
    "id": 5,
    "name": "Jek",
    "status": "STUDENT"
  },
  {
    "id": 6,
    "name": "Jhon",
    "status": "STUDENT"
  },
  {
    "id": 7,
    "name": "Olga",
    "status": "STUDENT"
  },
  {
    "id": 8,
    "name": "Alina",
    "status": "STUDENT"
  },
  {
    "id": 9,
    "name": "Veronika",
    "status": "STUDENT"
  }
]
```

___Swagger___

![swagger.png](src%2Fmain%2Fresources%2Fimg%2Fswagger.png)

### My application requests in Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/21948648/2sA3kSni9f)
  
