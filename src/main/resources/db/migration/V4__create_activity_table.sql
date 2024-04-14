CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS activity (
  activity_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID NOT NULL,
  activity_type VARCHAR(255), -- Added to match the Java model
  start_time TIMESTAMP, -- Changed to TIMESTAMP
  end_time TIMESTAMP -- Changed to TIMESTAMP
);
