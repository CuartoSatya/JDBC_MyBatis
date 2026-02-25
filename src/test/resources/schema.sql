create TABLE IF NOT EXISTS student
(
    id_student int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name varchar(100) NOT NULL,
    kana_name varchar(100),
    nickname varchar(100),
    mail_address varchar(100),
    address varchar(100),
    age int,
    sex varchar(100),
    remark varchar(255),
    course varchar(100),
    deleted tinyint NOT NULL DEFAULT 0
);

create TABLE IF NOT EXISTS studentcourse
(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_id int NOT NULL,
    name varchar(100),
    starting_date date,
    assured_finishing_date date
);
