insert into role(id, role_name)
values (1, 'DIRECTOR'),
       (2, 'MANAGER'),
       (3, 'BRANCH_MANAGER'),
       (4, 'SIM_CARD_MANAGER'),
       (5, 'EMPLOYEE_MANAGER'),
       (6, 'TARIFF_MANAGER'),
       (7, 'SIM_CARD'),
       (8, 'SIMPLE_WORKER');
INSERT INTO public.branch (address, name)
VALUES ('Toshkent', 'Bosh ofis');
insert into employer(id,account_non_expired, account_non_locked, create_at, credentials_non_expired, enabled, full_name, password, update_at, username, workplace_id)
values (1,true,true,'17-08-2021',true,true,'Sattorov Bekzodjon','270697','17-08-2021','bekdev',1);
insert into employer_roles(employer_id, roles_id) values (1,1);
