CREATE TABLE IF NOT EXISTS heartbeat_data (
  timestamp BIGINT,
  heartbeat INT,
  activity_id UUID,
  FOREIGN KEY (activity_id) REFERENCES activity(id)
);