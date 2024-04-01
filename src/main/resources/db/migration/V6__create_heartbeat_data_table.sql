CREATE TABLE IF NOT EXISTS heartbeat_data (
  heartbeat_data_id BIGINT PRIMARY KEY,
  timestamp BIGINT,
  heartbeat INT,
  activity_id BIGINT NOT NULL
);