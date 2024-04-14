CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS position_data (
  position_data_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  timestamp TIMESTAMP, -- Changed from BIGINT to TIMESTAMP
  latitude BIGINT,
  longitude BIGINT,
  height INT,
  activity_id UUID NOT NULL
);