CREATE TABLE IF NOT EXISTS position_data (
  position_data_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  timestamp BIGINT,
  latitude BIGINT,
  longitude BIGINT,
  height INT,
  activity_id BIGINT NOT NULL
);