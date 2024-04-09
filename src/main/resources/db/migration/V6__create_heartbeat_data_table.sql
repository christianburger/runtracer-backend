CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS heartbeat_data (
  heartbeat_data_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  timestamp BIGINT,
  heartbeat INT,
  activity_id UUID NOT NULL
);