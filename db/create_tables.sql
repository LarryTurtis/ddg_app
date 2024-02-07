CREATE TABLE IF NOT EXISTS entries (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT current_timestamp,
    userId UUID NOT NULL,
    comment TEXT NOT NULL,
    isPositive BOOLEAN NOT NULL
);