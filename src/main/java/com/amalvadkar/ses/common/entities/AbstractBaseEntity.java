package com.amalvadkar.ses.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;


    @PrePersist
    private void beforeAbstractPersist() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
        this.deleteFlag = false;
    }

    @PreUpdate
    private void beforeAbstractUpdate() {
        this.updatedOn = LocalDateTime.now();
    }
}
