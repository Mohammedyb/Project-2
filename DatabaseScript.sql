create table if not exists ROLES(
	id serial primary key,
	role varchar(30) not null unique
);

CREATE TABLE IF NOT EXISTS PROJECTS(
	id serial PRIMARY KEY,
	name varchar(40)
);

CREATE TABLE IF NOT EXISTS USERS(
	id serial primary key,
	email varchar(40) not null unique,
	password varchar(40),
	name varchar(40),
	project_id int4 references PROJECTS(id),
	role int4 references ROLES(id),
	auth_provider varchar(15)
);

create table if not exists MEETING_TYPE(
	id serial primary key,
	meeting_type varchar(20) not null unique
);

CREATE TABLE IF NOT EXISTS MEETINGS(
	id serial PRIMARY KEY,
	project_id int4 REFERENCES PROJECTS(id),
	meeting_date DATE,
	meeting_time TIME,
	meeting_type int4 REFERENCES MEETING_TYPE
);

CREATE TABLE IF NOT EXISTS TASKS(
	id serial PRIMARY KEY,
	name varchar(40),
	description varchar(60),
	due_date date,
	due_time time,
	assigned_user int4 REFERENCES USERS(id),
	project_id int4 references PROJECTS(id)
);

alter table PROJECTS add project_manager int4 REFERENCES USERS(id);
--ALTER TABLE public.roles ALTER COLUMN "role" TYPE varchar(30) USING "role"::varchar;


insert into ROLES (role) values ('Project Manager'), ('Team Member');
insert into PROJECTS (name) values ('Equifax Mobile Credit Report App');
insert into USERS (email, password, name, project_id, role) values ('project02sender@gmail.com', 'P@$$w0rd123', 'August Duet', 1,1);
insert into USERS (email, password, name, project_id, role) values ('christian.h@revature.net', 'P@$$w0rd123', 'Christian Hall', 1,2);
insert into meeting_type (meeting_type) values ('Daily Standup'), ('Sprint Review'),('Sprint Planning');
insert into MEETINGS (project_id, meeting_date, meeting_time, meeting_type) values (1, '2022-02-25', '12:00:00', 1);
insert into TASKS (name, description, due_date, due_time, assigned_user, project_id) values ('Setup Database', 'Setup Postgresql database with GCP and implement schema.' ,'2022-02-26', '11:30:00', 2, 1);

--ALTER TABLE public.users ADD auth_provider varchar(15) NULL;
alter sequence ROLES_id_seq restart with 1;
alter sequence MEETINGS_id_seq restart with 1;
alter sequence PROJECTS_id_seq restart with 1;
alter sequence USERS_id_seq restart with 1;
alter sequence TASKS_id_seq restart with 1;
alter sequence MEETING_TYPE_id_seq restart with 1;

DELETE FROM public.projects
WHERE id=2;


