insert into users (id, user_name, mobile_number, user_type) select 'a1b07c6d-4839-457e-8499-e87ececbea20', 'vikshak', '+911111111111',0 where not exists(select user_name from users where mobile_number = '+911111111111');
insert into users (id, user_name, mobile_number, user_type) select 'b1b07c6d-4839-457e-8499-e87ececbea20', 'sainik', '+912222222222',0 where not exists(select user_name from users where mobile_number = '+912222222222');
insert into users (id, user_name, mobile_number, user_type) select 'c1b07c6d-4839-457e-8499-e87ececbea20', 'Edition', '+913333333333',0 where not exists(select user_name from users where mobile_number = '+913333333333');
commit;