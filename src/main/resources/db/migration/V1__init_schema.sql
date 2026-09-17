CREATE TABLE regions (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    CONSTRAINT uk_regions_name UNIQUE (name)
);

CREATE TABLE districts (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    region_id   BIGINT NOT NULL,
    CONSTRAINT fk_districts_region FOREIGN KEY (region_id) REFERENCES regions (id)
);

CREATE INDEX idx_districts_region_id ON districts (region_id);

CREATE TABLE departments (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    CONSTRAINT uk_departments_name UNIQUE (name)
);

CREATE TABLE positions (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    CONSTRAINT uk_positions_name UNIQUE (name)
);

CREATE TABLE employees (
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(32) NOT NULL,
    region_id       BIGINT,
    district_id     BIGINT,
    department_id   BIGINT,
    position_id     BIGINT,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uk_employees_phone_number UNIQUE (phone_number),
    CONSTRAINT fk_employees_region FOREIGN KEY (region_id) REFERENCES regions (id),
    CONSTRAINT fk_employees_district FOREIGN KEY (district_id) REFERENCES districts (id),
    CONSTRAINT fk_employees_department FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_employees_position FOREIGN KEY (position_id) REFERENCES positions (id)
);

CREATE INDEX idx_employees_region_id ON employees (region_id);
CREATE INDEX idx_employees_district_id ON employees (district_id);
CREATE INDEX idx_employees_department_id ON employees (department_id);

CREATE TABLE location_pings (
    id              BIGSERIAL PRIMARY KEY,
    employee_id     BIGINT NOT NULL,
    latitude        DOUBLE PRECISION NOT NULL,
    longitude       DOUBLE PRECISION NOT NULL,
    accuracy        REAL,
    recorded_at     TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_location_pings_employee FOREIGN KEY (employee_id) REFERENCES employees (id)
);

CREATE INDEX idx_location_pings_employee_recorded_at ON location_pings (employee_id, recorded_at);

CREATE TABLE stops (
    id                  BIGSERIAL PRIMARY KEY,
    employee_id         BIGINT NOT NULL,
    latitude            DOUBLE PRECISION NOT NULL,
    longitude           DOUBLE PRECISION NOT NULL,
    address             VARCHAR(500),
    arrival_time        TIMESTAMPTZ NOT NULL,
    departure_time      TIMESTAMPTZ NOT NULL,
    duration_minutes    INTEGER NOT NULL,
    CONSTRAINT fk_stops_employee FOREIGN KEY (employee_id) REFERENCES employees (id)
);

CREATE INDEX idx_stops_employee_arrival_time ON stops (employee_id, arrival_time);

CREATE TABLE admin_users (
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(255) NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    CONSTRAINT uk_admin_users_username UNIQUE (username)
);
