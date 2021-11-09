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
delete from category where no = 4;

select distinct name from category where blog_id = 'eogkdfh@gmail.com';

select * from category;

desc post;

insert into post values(null, 'test', 'test', now(), 10);

select * from post;
delete from post where no = 1;

select p.no, p.title, p.contents, p.reg_date as regDate, p.category_no as catgoryNo
from post p join category c on p.category_no = c.no
where c.blog_id = 'eogkdfh@gmail.com'
order by regDate desc;