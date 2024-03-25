CREATE TABLE IF NOT EXISTS position_data (
  timestamp BIGINT,
  latitude BIGINT,
  longitude BIGINT,
  height INT,
  activity_id UUID,
  FOREIGN KEY (activity_id) REFERENCES activity(id)
);