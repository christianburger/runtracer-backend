CREATE TABLE IF NOT EXISTS step_data (
  timestamp BIGINT,
  steps INT,
  activity_id UUID,
  FOREIGN KEY (activity_id) REFERENCES activity(id)
);