CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS step_data (
  step_data_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  timestamp BIGINT,
  steps INT,
  activity_id UUID NOT NULL
);