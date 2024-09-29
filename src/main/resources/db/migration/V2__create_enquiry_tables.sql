create TABLE enquiry (
  id BIGINT AUTO_INCREMENT primary key NOT NULL,
  name VARCHAR(255) not null,
  contact_number VARCHAR(255) not null,
  class_mode VARCHAR(255) NULL,
  enquiry_date datetime not null,
  enquiry_status VARCHAR(255) not null,
  created_on datetime not null,
  updated_on datetime not null,
  created_by bigint,
  updated_by bigint,
  delete_flag BIT(1) not null
);

create TABLE course (
  id BIGINT AUTO_INCREMENT primary key NOT NULL,
  name VARCHAR(255) not null,
  delete_flag BIT(1) not null,
  created_on datetime not null,
  updated_on datetime not null,
  created_by bigint,
  updated_by bigint
);

create TABLE enquiry_course (
  id BIGINT AUTO_INCREMENT primary key NOT NULL,
  enquiry_id BIGINT not null,
  course_id BIGINT not null,
  delete_flag BIT(1) not null,
  created_on datetime not null,
  updated_on datetime not null,
  created_by bigint,
  updated_by bigint
);

alter table enquiry_course add CONSTRAINT `fk_enquiry_course_course` FOREIGN KEY (`course_id`) REFERENCES course (`id`);
alter table enquiry_course add CONSTRAINT `fk_enquiry_course_enquiry` FOREIGN KEY (`enquiry_id`) REFERENCES enquiry (`id`);



