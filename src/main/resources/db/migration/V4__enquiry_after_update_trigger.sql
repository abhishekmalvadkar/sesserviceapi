DELIMITER $$

CREATE TRIGGER after_enquiry_update
AFTER UPDATE ON enquiry
FOR EACH ROW
BEGIN
   insert into enquiry_history
   (
        history_time,
        enquiry_id,
        name,
        contact_number,
        class_mode,
        enquiry_date,
        enquiry_status,
        created_on,
        updated_on,
        created_by,
        updated_by,
        delete_flag
   ) values
   (
       now(),
       old.id,
       old.name,
       old.contact_number,
       old.class_mode,
       old.enquiry_date,
       old.enquiry_status,
       old.created_on,
       old.updated_on,
       old.created_by,
       old.updated_by,
       old.delete_flag
   );
END$$

DELIMITER ;
