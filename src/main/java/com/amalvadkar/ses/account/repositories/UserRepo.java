package com.amalvadkar.ses.account.repositories;

import com.amalvadkar.ses.account.constants.AccountErrConstants;
import com.amalvadkar.ses.account.entities.UserEntity;
import com.amalvadkar.ses.account.exceptions.InvalidTempPasswordException;
import com.amalvadkar.ses.account.exceptions.UnauthorizedException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.amalvadkar.ses.account.constants.AccountErrConstants.INVALID_TEMP_PASSWORD_ERR_MSG;

public interface UserRepo extends JpaRepository<UserEntity,Long> {

    boolean existsByEmail(String email);//email

    @Query("""
            select u from UserEntity u
            where email = :email
            and active = true
            and deleteFlag = false
            """)
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("""
            select u from UserEntity u
            where email = :email
            and tempPassword = :temPassword
            and active = true
            and deleteFlag = false
            """)
    Optional<UserEntity>  findByEmailAndTempPassword(@Param("email") String email,
                                                     @Param("temPassword") String tempPassword);

    default UserEntity getUserEntityByEmailAndTempPassword(String email, String tempPassword) {
        return findByEmailAndTempPassword(email, tempPassword).
                orElseThrow(() -> new InvalidTempPasswordException(INVALID_TEMP_PASSWORD_ERR_MSG));
    }

    default UserEntity findByEmailOrThrowUnauthorizedException(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException(AccountErrConstants.INVALID_USERNAME_OR_PASSWORD_ERR_MSG));
    }
}
