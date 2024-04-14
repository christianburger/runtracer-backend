CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS step_data (
  step_data_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  timestamp TIMESTAMP, -- Changed from BIGINT to TIMESTAMP
  steps INT,
  activity_id UUID NOT NULL
);