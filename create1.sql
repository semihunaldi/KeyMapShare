create table t_keymap (id varchar(255) not null, create_time timestamp, deleted varchar(255), name varchar(255), path varchar(255), user_id varchar(255), primary key (id))
create table t_user (id varchar(255) not null, create_time timestamp, deleted varchar(255), email varchar(255), git_hub_id varchar(255), last_token varchar(255), password varchar(255), primary key (id))
alter table t_keymap add constraint T_KEYMAP_NAME_UNIQUE_ID unique (name, user_id)
alter table t_user add constraint T_USER_EMAIL_UNIQUE_ID unique (email)
alter table t_keymap add constraint FK8j7uu0e8c4qnok4v2xi4qtule foreign key (user_id) references t_user
