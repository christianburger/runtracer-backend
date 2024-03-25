CREATE TABLE IF NOT EXISTS activity (
  id UUID PRIMARY KEY,
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);