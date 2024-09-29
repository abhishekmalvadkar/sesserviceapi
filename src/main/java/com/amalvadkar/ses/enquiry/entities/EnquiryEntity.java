package com.amalvadkar.ses.enquiry.entities;


import com.amalvadkar.ses.common.entities.AbstractBaseEntity;
import com.amalvadkar.ses.enquiry.enums.EnquiryStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "enquiry")
public class EnquiryEntity extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "class_mode")
    private String classMode;

    @Column(name = "enquiry_status")
    private String enquiryStatus;

    @Column(name = "enquiry_date", updatable = false)
    private LocalDateTime enquiryDate;

    @PrePersist
    public void preEnquiryPersist() {
        this.enquiryStatus=EnquiryStatusEnum.NEW.value();
        this.enquiryDate = LocalDateTime.now();
    }

}
