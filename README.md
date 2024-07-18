# ASTON-JAVA-SERVLET-REST

# RESTful Web-application is written using the following technologies:

- Servlet API
- JDBC
- FlyWay
- PostgresSQL DB

### Short description of the project

- Standalone application providing REST API

### Prerequisites:

- Java 19
- PostgresSQL

# Data model

## ER diagram for the data model

![db.png](src%2Fmain%2Fresources%2Fimg%2Fdb.png)

## RESTful API

**1. API Description of general methods for Course**

| METHOD | PATH                                                            | DESCRIPTION                |
|--------|-----------------------------------------------------------------|----------------------------|
| POST   | /api/courses                                                    | create new course          |
| POST   | /api/students/courses?idStudent={studentId}&idCourse={courseId} | add student in course      |
| PUT    | /api/courses?id={courseId}                                      | update name course         |
| PUT    | /api/courses?courseId={courseId}&teacherId={teacherId}          | add teacher in course      |
| GET    | /api/courses                                                    | get all courses            |
| GET    | /api/course?id={id}                                             | get course by id           |
| DELETE | /api/courses?id={id}                                            | delete course by id        |
| DELETE | /api/students/courses?idStudent={studentId}&idCourse={courseId} | delete student from course |

___POST: Request body for method create new student___

```json
{
  "name": "IT-Overone"
}
```

```json
{
  "id": 1,
  "name": "IT-Overone"
}
```

___GET: course by id___

```json
{
    "id": 1,
    "name": "IT-Overone",
    "students": [
        {
            "id": 1,
            "name": "Bob",
            "status": "STUDENT"
        },
        {
            "id": 2,
            "name": "Bill",
            "status": "STUDENT"
        },
        {
            "id": 3,
            "name": "Maks",
            "status": "STUDENT"
        },
        {
            "id": 4,
            "name": "Alex",
            "status": "STUDENT"
        },
        {
            "id": 5,
            "name": "Andrey",
            "status": "STUDENT"
        },
        {
            "id": 6,
            "name": "Alina",
            "status": "STUDENT"
        },
        {
            "id": 7,
            "name": "Victoria",
            "status": "STUDENT"
        },
        {
            "id": 8,
            "name": "Mary",
            "status": "STUDENT"
        },
        {
            "id": 9,
            "name": "Nazar",
            "status": "STUDENT"
        },
        {
            "id": 10,
            "name": "Mark",
            "status": "STUDENT"
        }
    ],
    "teacher": {
        "id": 1,
        "name": "Dmitriy Palchunsry",
        "status": "TEACHER",
        "students": [
            {
                "id": 1,
                "name": "Bob",
                "status": "STUDENT"
            },
            {
                "id": 2,
                "name": "Bill",
                "status": "STUDENT"
            },
            {
                "id": 3,
                "name": "Maks",
                "status": "STUDENT"
            },
            {
                "id": 4,
                "name": "Alex",
                "status": "STUDENT"
            },
            {
                "id": 5,
                "name": "Andrey",
                "status": "STUDENT"
            },
            {
                "id": 6,
                "name": "Alina",
                "status": "STUDENT"
            },
            {
                "id": 7,
                "name": "Victoria",
                "status": "STUDENT"
            },
            {
                "id": 8,
                "name": "Mary",
                "status": "STUDENT"
            },
            {
                "id": 9,
                "name": "Nazar",
                "status": "STUDENT"
            },
            {
                "id": 10,
                "name": "Mark",
                "status": "STUDENT"
            }
        ]
    }
}
```

**2. API Description of general methods for Teacher**

| METHOD | PATH                  | DESCRIPTION          |
|--------|-----------------------|----------------------|
| POST   | /api/teachers         | create new teacher   |
| GET    | /api/teachers         | get all teachers     |
| GET    | /api/teachers?id={id} | get teacher by id    |
| DELETE | /api/courses?id={id}  | delete teacher by id |


___GET: get teacher by id___

```json
{
    "id": 1,
    "name": "Dmitriy Palchinsky",
    "status": "TEACHER",
    "students": [
        {
            "id": 1,
            "name": "Bob",
            "status": "STUDENT"
        },
        {
            "id": 2,
            "name": "Bill",
            "status": "STUDENT"
        },
        {
            "id": 3,
            "name": "Maks",
            "status": "STUDENT"
        },
        {
            "id": 4,
            "name": "Alex",
            "status": "STUDENT"
        },
        {
            "id": 5,
            "name": "Andrey",
            "status": "STUDENT"
        },
        {
            "id": 6,
            "name": "Alina",
            "status": "STUDENT"
        },
        {
            "id": 7,
            "name": "Victoria",
            "status": "STUDENT"
        },
        {
            "id": 8,
            "name": "Mary",
            "status": "STUDENT"
        },
        {
            "id": 9,
            "name": "Nazar",
            "status": "STUDENT"
        },
        {
            "id": 10,
            "name": "Mark",
            "status": "STUDENT"
        }
    ]
}
```

**3. API Description of general methods for Student**

| METHOD | PATH                  | DESCRIPTION          |
|--------|-----------------------|----------------------|
| POST   | /api/students         | create new student   |
| GET    | /api/students         | get all students     |
| GET    | /api/students?id={id} | get student by id    |
| DELETE | /api/students?id={id} | delete student by id |

___POST: Request body for method create new student___

```json
{
  "name": "Mark"
}
```

___Response___

```json
{
  "id": 10,
  "name": "Mark",
  "status": "STUDENT"
}
```

___GET: get all students___

```json
[
  {
    "id": 1,
    "name": "Bob",
    "status": "STUDENT"
  },
  {
    "id": 2,
    "name": "Bill",
    "status": "STUDENT"
  },
  {
    "id": 3,
    "name": "Maks",
    "status": "STUDENT"
  },
  {
    "id": 4,
    "name": "Alex",
    "status": "STUDENT"
  },
  {
    "id": 5,
    "name": "Andrey",
    "status": "STUDENT"
  },
  {
    "id": 6,
    "name": "Alina",
    "status": "STUDENT"
  },
  {
    "id": 7,
    "name": "Victoria",
    "status": "STUDENT"
  },
  {
    "id": 8,
    "name": "Mary",
    "status": "STUDENT"
  },
  {
    "id": 9,
    "name": "Nazar",
    "status": "STUDENT"
  },
  {
    "id": 10,
    "name": "Mark",
    "status": "STUDENT"
  }
]
```

### My application requests in Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/21948648/2sA3kSni9f)
  
