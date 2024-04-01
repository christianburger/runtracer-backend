CREATE TABLE IF NOT EXISTS movement_data (
  movement_data_id BIGINT PRIMARY KEY,
  timestamp BIGINT,
  move_up INT,
  move_down INT,
  move_left INT,
  move_right INT,
  activity_id BIGINT NOT NULL
);