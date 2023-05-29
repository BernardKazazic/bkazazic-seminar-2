CREATE TABLE reading
(
    id           SERIAL PRIMARY KEY,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    active_power DECIMAL(5, 2)
);