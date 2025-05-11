alter table credits add column close_date date;
alter table credits drop column credit_period;

update credits c set close_date = '2025-01-01' where c.id = 1;
update credits c set close_date = '2027-01-01' where c.id = 2;