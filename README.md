# Hello World API

A small HTTP API that greets a name when it starts in the first half of the English alphabet.

`GET /hello-world?name=alice` returns:

```json
{ "message": "Hello Alice" }
```

Names that are missing, blank, or start with `N`–`Z` (or any other character) return HTTP 400:

```json
{ "error": "Invalid Input" }
```

## Requirements

- JDK 21
- Maven is not required globally. The repo includes the Maven Wrapper (`./mvnw`).

## Run the application

```bash
./mvnw spring-boot:run
```

The server listens on port 8080.

```bash
curl "http://localhost:8080/hello-world?name=alice"
curl "http://localhost:8080/hello-world?name=nathan"
curl "http://localhost:8080/hello-world"
```

## Run the tests

```bash
./mvnw test
```

`GreetingServiceTest` checks the naming rule without Spring. `HelloWorldControllerTest` checks the HTTP status and JSON body. `HelloWorldApplicationTests` checks that the application context starts.

## Assumptions

- The check uses the English alphabet only. `A`–`M` is accepted, ignoring case. `N`–`Z`, digits, symbols, and letters outside `A`–`Z` (for example `é`) are rejected.
- Leading and trailing whitespace is removed with `String.strip()` before the name is checked. A value that is empty after that is invalid.
- Only the first character is capitalized in the greeting. The rest of the name is left as sent, so `alice` becomes `Hello Alice` and `aLiCe` becomes `Hello ALiCe`.
- The query parameter name is case-sensitive. `Name` does not satisfy `name`.
- If `name` is repeated, Spring MVC binds the first value.
- Classification does not depend on the JVM locale. The first character is uppercased with `Character.toUpperCase(char)` and then compared with the ASCII range `A`–`M`.
