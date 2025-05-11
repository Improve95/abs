alter table reset_password_requests
    add column user_id int references users(id);
alter table reset_password_requests
    add constraint unique_token unique (token);