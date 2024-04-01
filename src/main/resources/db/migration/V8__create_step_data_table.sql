CREATE TABLE IF NOT EXISTS step_data (
  step_data_id BIGINT PRIMARY KEY,
  timestamp BIGINT,
  steps INT,
  activity_id BIGINT NOT NULL
);