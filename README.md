# Employee Location Tracking Service

Spring Boot backend for the employee location tracking admin system. Serves the
Android field app (registration + location ingestion) and the React admin
dashboard (reference data CRUD, employee management, route/stop retrieval, live
location, and Excel reporting).

## Tech stack

- Java 21, Spring Boot 3
- Spring Web, Spring Data JPA, Spring Security (JWT)
- Spring WebSocket (STOMP) for live location broadcast
- Apache POI for .xlsx report generation
- Yandex Geocoder HTTP API for reverse geocoding
- PostgreSQL + Flyway
- Maven

## Prerequisites

- JDK 21
- Maven 3.9+
- A running PostgreSQL instance with a `location_tracking` database

```bash
createdb location_tracking
```

## Environment variables

All configuration has a local default, so the service runs out of the box against
a local Postgres on `localhost:5432`. Override any of these for other environments:

| Variable               | Default                                              | Description                          |
|-------------------------|-------------------------------------------------------|---------------------------------------|
| `SERVER_PORT`           | `8082`                                                 | HTTP port                             |
| `DB_URL`                | `jdbc:postgresql://localhost:5432/location_tracking`  | JDBC URL                              |
| `DB_USERNAME`           | `postgres`                                             | Database username                     |
| `DB_PASSWORD`           | `postgres`                                             | Database password                     |
| `JWT_SECRET`            | `dev-only-secret-override-in-prod`                     | HMAC signing secret — set a real value in prod |
| `JWT_EXPIRATION_MS`     | `86400000` (24h)                                       | JWT lifetime in milliseconds          |
| `CORS_ALLOWED_ORIGINS`  | `http://localhost:3000`                                | Comma-separated list of allowed origins for the admin dashboard and the STOMP endpoint |
| `SERVER_TIME_ZONE`      | `UTC`                                                  | Time zone used to interpret the `date`/`from`/`to` params on the route, stops and report endpoints |
| `YANDEX_GEOCODER_API_KEY` | *(empty)*                                            | API key for the Yandex Geocoder. If left empty, stop addresses fall back to raw coordinates instead of failing |
| `YANDEX_GEOCODER_BASE_URL` | `https://geocode-maps.yandex.ru/1.x/`                | Base URL of the Yandex Geocoder HTTP API |
| `STOP_RADIUS_METERS`    | `100`                                                   | Max distance (meters) between consecutive pings for them to count as the same stop |
| `STOP_MIN_DURATION_MINUTES` | `10`                                                | Minimum time span a group of pings must cover to be considered a stop |

## Running locally

```bash
mvn spring-boot:run
```

Flyway runs automatically on startup and creates the full schema plus the seeded
admin user and Uzbekistan's regions/districts reference data. The service
listens on `http://localhost:8082` by default.

## Seeded admin credentials

| Username | Password   |
|----------|------------|
| `admin`  | `admin123` |

Log in via `POST /api/auth/login` with this username/password to obtain a JWT.
Change this password (or rotate the admin user) before using this in production —
it exists purely so the dashboard has a way in on a fresh environment.

## Reference data

`regions` and `districts` are seeded with Uzbekistan's full administrative
division (`V3__seed_uzbekistan_regions_and_districts.sql`): the Republic of
Karakalpakstan, the 12 regions, and Tashkent City, each with its districts —
175 districts in total. Departments and positions are left empty for the
admin to define per organization.

## Pointing the Android app at this backend

Configure the app's server base URL to `http://<host>:8082` (or wherever you deploy
this service). The endpoints the app calls do not require authentication:

- `POST /api/employees/register` — registers/updates the employee identity record
- `POST /api/locations` — periodic location pings, keyed by `deviceId` (the
  employee's phone number)
- `GET /api/regions`, `GET /api/districts` — reference data needed to fill the
  registration form

Every other `/api/**` endpoint requires the admin dashboard's JWT
(`Authorization: Bearer <token>`), obtained via `POST /api/auth/login`.

## API surface (part 1)

- `POST /api/auth/login`
- `GET/POST/PUT/DELETE /api/regions`, `/api/regions/{id}`
- `GET/POST/PUT/DELETE /api/districts?regionId=`, `/api/districts/{id}`
- `GET/POST/PUT/DELETE /api/departments`, `/api/departments/{id}`
- `GET/POST/PUT/DELETE /api/positions`, `/api/positions/{id}`
- `POST /api/employees/register`
- `GET /api/employees?regionId=&districtId=&departmentId=`
- `GET /api/employees/{id}`
- `PATCH /api/employees/{id}/assignment`
- `DELETE /api/employees/{id}` (deactivates, does not hard-delete)
- `GET /api/employees/{id}/route?date=YYYY-MM-DD`
- `GET /api/employees/{id}/stops?date=YYYY-MM-DD`
- `GET /api/employees/{id}/current-location`
- `GET /api/reports/employee/{id}?from=YYYY-MM-DD&to=YYYY-MM-DD` (downloads an .xlsx)
- `GET /api/reports/department/{id}?from=YYYY-MM-DD&to=YYYY-MM-DD` (downloads an .xlsx)
- `POST /api/locations`
- STOMP endpoint `/ws` (SockJS), topic `/topic/locations/{employeeId}` — broadcasts
  every ping ingested via `POST /api/locations` for that employee, for live
  dashboard updates without polling

## Stop detection

Consecutive location pings for an employee are grouped while they stay within
`tracking.stop-detection.radius-meters` of the group's centroid (Haversine
distance). A group becomes a stop once its time span reaches
`tracking.stop-detection.min-duration-minutes`. Each stop's centroid is
reverse-geocoded once via the Yandex Geocoder and cached on the `stops` row, so
repeat requests for the same day don't re-geocode.

## Reports

Both report endpoints stream an `.xlsx` workbook with one row per stop:
employee, phone number, department, position, date, arrival/departure time,
duration, and the reverse-geocoded address. The address cell is a hyperlink
that opens the exact point on Yandex Maps. The department report includes a
row per stop per employee in that department, with an employee column.
# location-backend
