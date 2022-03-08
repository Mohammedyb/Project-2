create table if not exists ROLES(
	id serial primary key,
	role varchar(20) not null unique
);

CREATE TABLE IF NOT EXISTS PROJECTS(
	id serial PRIMARY KEY,
	name varchar(40),
	project_manager_id int4,
	project_manager varchar(20),
	project_description varchar(255),
	deadline varchar(10)
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
	project_id int4,
	meeting_date varchar(10),
	meeting_time varchar(8),
	meeting_type int4
);

CREATE TABLE IF NOT EXISTS TASKS(
	id serial PRIMARY KEY,
	name varchar(40),
	description varchar(60),
	due_date varchar(10),
	due_time varchar(8),
	assigned_user int4,
	project_id int4
);

create table if not exists ASSIGN_PROJECT (
	id serial primary key,
	projects_id int4,
	project_manager varchar(40),
	assign_user_id int4,
	assign_user_name varchar(40)
);

create table if not exists TASK_PROGRESS (
	id serial primary key,
	assign_task_id int4,
	projects_id int4,
	progress_status varchar(12),
	task_comment varchar(255)
);

--ALTER TABLE public.roles ALTER COLUMN "role" TYPE varchar(30) USING "role"::varchar;
-- ALTERS TO ALLOW CALENDAR FUNCTION
ALTER TABLE MEETINGS DROP COLUMN meeting_date;
ALTER TABLE MEETINGS DROP COLUMN meeting_time;
ALTER TABLE MEETINGS ADD COLUMN meeting_calendar_id varchar NOT NULL default 'NONE';
ALTER TABLE MEETINGS ADD COLUMN meeting_length decimal NOT NULL default 1.5;
ALTER TABLE MEETINGS ADD COLUMN meeting_link varchar NOT NULL DEFAULT 'NONE';
ALTER TABLE MEETINGS ADD COLUMN timestamp timestamp;
ALTER TABLE PROJECTS ADD COLUMN meeting_calendar_id varchar NOT NULL DEFAULT 'NONE';



insert into ROLES (role) values ('Project Manager'), ('Team Member');
insert into PROJECTS (name, project_manager_id ,project_manager, project_description,deadline) values ('Equifax Mobile Credit Report App', 1, 'August Duet', 'Creating a mobile credit report for Equifax', '2022-03-04');
insert into USERS (email, password, name, project_id, role) values ('project02sender@gmail.com', 'password123', 'August Duet', 1,1);
insert into USERS (email, password, name, project_id, role) values ('christianphall@gmail.com', 'password123', 'Christian Hall', 1,2);
insert into USERS (email, password, name, project_id, role) values ('jiaying.li@revature.net', '123456', 'Jia ying Li', 1, 2);
insert into meeting_type (meeting_type) values ('Daily Standup'), ('Sprint Review'),('Sprint Planning');
insert into meetings (project_id, meeting_type) values (1, 1);
insert into tasks (name, description, due_date, due_time, assigned_user, project_id) values ('Setup Database', 'Setup Postgresql database with GCP and implement schema.' ,'2022-02-26', '11:30:00', 2, 1);
insert into assign_project (projects_id, project_manager, assign_user_id, assign_user_name) values (1, 'August Duet', 2, 'Christian Hall');
insert into task_progress (assign_task_id, projects_id, progress_status, task_comment) values (1, 1, 'DONE', 'completed and push to github');

alter sequence ROLES_id_seq restart with 1;
alter sequence MEETINGS_id_seq restart with 1;
alter sequence PROJECTS_id_seq restart with 1;
alter sequence USERS_id_seq restart with 1;
alter sequence TASKS_id_seq restart with 1;
alter sequence MEETING_TYPE_id_seq restart with 1;
alter sequence ASSIGN_PROJECT_id_seq restart with 1;
alter sequence TASK_PROGRESS_id_seq restart with 1;

drop table tasks;
drop table meetings;
drop table meeting_type;
drop table users cascade;
drop table projects cascade;
drop table roles cascade;
drop table assign_project;
drop table task_progress;

DELETE FROM public.users
WHERE id=4;

delete from public.projects
where id > 1;

DELETE FROM public.projects
WHERE id=2;


insert into tasks (name, description, due_date, due_time, assigned_user, project_id) values ('Setup Database', 'Setup Postgresql database with GCP and implement schema.' ,'2022-02-26', '11:30:00', 5, 1);
insert into tasks (name, description, due_date, due_time, assigned_user, project_id) values ('Setup Database', 'Setup Postgresql database with GCP and implement schema.' ,'2022-02-26', '11:30:00', 5, 1);

UPDATE public.meetings
SET project_id=1, meeting_type=1, meeting_calendar_id='NONE', meeting_length=1.5, meeting_link='NONE', "timestamp"='2022-03-08T08:30:00-05:00', attendees=NULL
WHERE id=1;



