# Job Fetcher

## Prerequisites

- JDK: version 20 (distribution temurin)

## Main commands

I want to run the test suite
`./gradlew test`

I want to run the application
```shell
# replace the <change-me> with your own access token
POLE_EMPLOI_API_TOKEN=<change-me> ./gradlew bootTestRun

# to get your own access token execute the following curl command (replace <change-me> with your own data)
curl --location --request POST 'https://entreprise.pole-emploi.fr/connexion/oauth2/access_token?realm=/partenaire' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=<change-me>' \
--data-urlencode 'client_secret=<change-me>' \
--data-urlencode 'scope=api_offresdemploiv2 o2dsoffre'
```
It will create a postgresql database in a docker container behind the hood. Thanks to Spring Boot 3.1.

I want to access to the database. The JDBC url is `jdbc:postgresql://localhost:<dynamic-port>/job`. The credentials are:
- the username is `admin`
- the password is `change-me`.
They are defined [here](TODO)

## Information

### Lombok

Here I'm using lombok but in the real life I'm not a big fan of this library. Some upgrades of lombok can be challenging and sometimes there is conflicts with other libs (for example with Mapstruct). I prefer writing the good old POJOs by myself (with an IDE it's super fast)

### Equals and hashcode on jpa entity

They have been generated using JpaBuddy utility

### Git commits

I write commits as an history so it can help people to understand what the application does.

### Architecture

I'm using hexagonal architecture.

### Testing

I'm using testing use case concept. Check out this [great presentation](https://optivem.com/wp-content/uploads/2022/05/Optivem-TDD-and-Clean-Architecture-Use-Case-Driven-Development.pptx.pdf) about this topic. I'm using approach 2 (slide 46).

### Development methodology

I used real TDD during the development of this project.
