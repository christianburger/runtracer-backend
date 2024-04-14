CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS movement_data (
  movement_data_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  timestamp TIMESTAMP, -- Changed from BIGINT to TIMESTAMP
  move_up INT,
  move_down INT,
  move_left INT,
  move_right INT,
  activity_id UUID NOT NULL
);