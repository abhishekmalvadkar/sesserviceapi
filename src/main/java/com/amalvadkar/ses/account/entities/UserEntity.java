package com.amalvadkar.ses.account.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "temp_password")
    private String tempPassword;

    @Column(name = "phone_no")
    private String phone;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "active")
    private Boolean active;

    @PrePersist
    private void preUserPersist() {
        this.active = true;
        this.isLocked = true;
    }

}
