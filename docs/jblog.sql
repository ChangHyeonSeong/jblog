desc user;
desc 
insert 
into user 
values('bolt', '창현', 1234, now());

select * from user;
delete from user where id = 'bolt';

select * from blog;


desc category;
select c.no, c.name, c.desc, c.blog_id as blogId from category c;

insert into category values(null, 'test', 'java', 'nami');