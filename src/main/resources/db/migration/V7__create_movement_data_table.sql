CREATE TABLE IF NOT EXISTS movement_data (
  timestamp BIGINT,
  up INT,
  down INT,
  move_left INT,
  move_right INT,
  activity_id UUID,
  FOREIGN KEY (activity_id) REFERENCES activity(id)
);