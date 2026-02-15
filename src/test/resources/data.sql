INSERT INTO student (name, kana_name, nickname, mail_address, address, age, sex, remark, course, deleted)
VALUES ('Tanaka Taro','Tanaka Taro','Taro','TTallow@ymobile.ne.jp',
        'Okayama',45,'1','','',0),
       ('Yamamoto Naomi','Yamamoto Naomi','Naomi','YNaomi@nttdocomo.ne.jp',
        'Okayama',36,'2','','',0),
       ('Asada Jyunichi','Asada Jyunichi','Jyunichi','DDbeam@network.ne.jp',
        'Osaka',40,'1','','',0),
       ('Nirayama Fumi','Nirayama Fumi','Fumichan','Vortex@ktt.ne.jp',
        'Okayama',40,'2','','',0),
       ('Yamada Jiro','Yamada jiro','Jiro','GlassHopper@yahoo.ne.jp',
        'Okayama',35,'1','','',1),
       ('Furutori Ao','Furutori Ao','Ao','Ao@higashi.com',
        'Ibaraki',30,'1','','',0),
       ('Enami Kouji','Enami Kouji','Kouji','EnamiK@rink-ac.com',
        'Nara',34,'1','','',0);

INSERT INTO studentcourse (student_id,name,starting_date,assured_finishing_date)
VALUES (2,'WEBdesigningCourse','2024-01-04','2025-02-12'),
       (1,'AwsCourse',NULL,NULL),
       (3,'AwsCourse','2024-01-14','2024-12-26'),
       (3,'MarcketingCourse','2024-01-16','2025-01-25'),
       (4,'GraphicCourse','2024-03-25','2025-03-02'),
       (6,'JavaCourse','2025-10-01','2025-12-31'),
       (7,'WEBdesigningCourse',NULL,NULL);
