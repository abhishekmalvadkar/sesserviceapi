package com.amalvadkar.ses.enquiry.entities;

import com.amalvadkar.ses.common.entities.AbstractBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="enquiry_course")
@Getter
@Setter
public class EnquiryCourseEntity extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="enquiry_id")
    private EnquiryEntity enquiryEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private CourseEntity courseEntity;

}
