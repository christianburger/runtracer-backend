ALTER TABLE user_roles
ADD FOREIGN KEY (user_id) REFERENCES users(user_id),
ADD FOREIGN KEY (role_id) REFERENCES roles(role_id);

ALTER TABLE activity
ADD FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE position_data
ADD FOREIGN KEY (activity_id) REFERENCES activity(activity_id);

ALTER TABLE heartbeat_data
ADD FOREIGN KEY (activity_id) REFERENCES activity(activity_id);

ALTER TABLE movement_data
ADD FOREIGN KEY (activity_id) REFERENCES activity(activity_id);

ALTER TABLE step_data
ADD FOREIGN KEY (activity_id) REFERENCES activity(activity_id);