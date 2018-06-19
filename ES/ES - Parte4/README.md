# Adventure Builder [![Build Status](https://travis-ci.com/tecnico-softeng/es18tg_10-project.svg?token=Ja9ARpCmmuMjhqpMwsro&branch=develop)](https://travis-ci.com/tecnico-softeng/es18tg_10-project)[![codecov](https://codecov.io/gh/tecnico-softeng/es18tg_10-project/branch/master/graph/badge.svg?token=ii8uGnK9a4)](https://codecov.io/gh/tecnico-softeng/es18tg_10-project)

To run tests execute: mvn clean install

To see the coverage reports, go to <module name>/target/site/jacoco/index.html.


|   Number   |          Name          |            Email             |   GitHub Username  | Group |
| ---------- | ---------------------- | ---------------------------- | -------------------| ----- |
|   71054    |    Nuno Pereira        |nuno.pereira4412@gmail.com    | nunopereira4412    |   1   |
|   79779    |    Rita Rocha          |anaritar30@gmail.com          | ritarocha30        |   1   |
|   80908    |    André Batista       |andrepeseiro@hotmail.com      | AndreBatista80908  |   1   |
|   70566    |    Ricardo Carreira    |ricardo.carreira.613@gmail.com| RicardoCarreira    |   2   |
|   70641    |    Bruno Duarte        |brunocas_duarte@hotmail.com   | KinslayerPT        |   2   |
|   74249    |    Catarina Fernandes  |catf.22@gmail.com             | catarinatfernandes |   2   |
|   79062    |    Heydi Marques       |heydimarques@outlook.pt       | heydimarques       |   2   |

- **Group 1: Car + Hotel + Activity**
- **Group 2: Tax + Bank + Broker**

### Infrastructure

This project includes the persistent layer, as offered by the FénixFramework.
This part of the project requires to create databases in mysql as defined in `resources/fenix-framework.properties` of each module.

See the lab about the FénixFramework for further details.

#### Docker (Alternative to installing Mysql in your machine)

To use a containerized version of mysql, follow these stesp:

```
docker-compose -f local.dev.yml up -d
docker exec -it mysql sh
```

Once logged into the container, enter the mysql interactive console

```
mysql --password
```

And create the 7 databases for the project as specified in
the `resources/fenix-framework.properties`.
