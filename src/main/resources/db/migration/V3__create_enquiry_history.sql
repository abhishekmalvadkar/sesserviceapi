create TABLE enquiry_history(
  id BIGINT AUTO_INCREMENT primary key,
  history_time datetime not null,
  enquiry_id bigint not null,
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

alter table enquiry_history add CONSTRAINT `fk_enquiry_history_enquiry` FOREIGN KEY (`enquiry_id`) REFERENCES enquiry (`id`);