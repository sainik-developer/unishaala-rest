insert into users (id, user_name, mobile_number, user_type) select 'a1b07c6d-4839-457e-8499-e87ececbea20', 'vikshak', '+919043408421',0 where not exists(select user_name from users where mobile_number = '+919043408421');
insert into users (id, user_name, mobile_number, user_type) select 'b1b07c6d-4839-457e-8499-e87ececbea20', 'sainik', '+919748087957',0 where not exists(select user_name from users where mobile_number = '+919748087957');
insert into users (id, user_name, mobile_number, user_type) select 'c1b07c6d-4839-457e-8499-e87ececbea20', 'Edition', '+918792286834',0 where not exists(select user_name from users where mobile_number = '+918792286834');
commit;

