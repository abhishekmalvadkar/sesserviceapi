package com.amalvadkar.ses.enquiry.entities;

import com.amalvadkar.ses.common.entities.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="course")
public class CourseEntity extends AbstractBaseEntity {

    @Column(name="name")
    private String name;
}
