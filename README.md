# Currency Exchange

This API provides endpoints to manage currencies, including saving currency rates, deleting rates by date, and
retrieving currency rates by date and currency.

## Steps to run

1. Set your own postgres credentials
2. You can run postgres image [docker compose up](docker-compose.yml)
3. Build the project using ` mvn clean install -D maven.test.skip=true`
4. Run using `mvn spring-boot:run`
5. The web application is accessible via `localhost:8080`

6. Constant token: `my-constant-secret-token`
7.  `Username: user`
8.  `Password: password`
---
## Endpoints

### Get Authentication Token
Get the authentication token.
### GET `/api/v1/token`
#### Example `http://localhost:8080/api/v1/token`

---

### POST `/api/v1/currencies`
#### EXAMPLE `http://localhost:8080/api/v1/currencies?date=11-02-2023`

Saves currency rates for a specific date.

#### Request Parameters

| Name | Type   | Description                       |
|------|--------|-----------------------------------|
| date | String | The date in the format dd-MM-yyyy |

#### Response

Returns a response with a status code 200 and a message indicating that the currency rates were saved successfully.

---

### DELETE `/api/v1/currencies`
#### Example `http://localhost:8080/api/v1/currencies?date=11-02-2023`

Deletes currency rates for a specific date.

#### Request Parameters

| Name | Type   | Description                       |
|------|--------|-----------------------------------|
| date | String | The date in the format dd-MM-yyyy |

#### Response

Returns a response with a status code 200 indicating that the currency rates were deleted successfully.

--- 

### GET `/api/v1/currencies/historical`

#### Example `http://localhost:8080/api/v1/currencies/historical?from=USD&date=11-02-2023`

#### Request Parameters

| Name | Type   | Description                                        |
|------|--------|----------------------------------------------------|
| date | String | The date in the format dd-MM-yyyy                  |
| from | String | The currency code to retrieve rates for (optional) |

#### Response

Returns a response with a status code 200 and a JSON object containing the currency rates for the requested date and
currency.

---

### GET `/api/v1/currencies`
#### EXAMPLE `http://localhost:8080/api/v1/currencies?from=USD`

#### Request Parameters

| Name | Type   | Description                             |
|------|--------|-----------------------------------------|
| from | String | The currency code to retrieve rates for |

#### Response

Returns a response with a status code 200 and a JSON object containing the dates and currency rates for the requested currency.E 