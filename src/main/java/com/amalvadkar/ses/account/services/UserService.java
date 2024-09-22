package com.amalvadkar.ses.account.services;


import com.amalvadkar.ses.account.constants.AccountErrConstants;
import com.amalvadkar.ses.account.entities.UserEntity;
import com.amalvadkar.ses.account.exceptions.AccountLockException;
import com.amalvadkar.ses.account.exceptions.ConfirmPasswordUnMatchedException;
import com.amalvadkar.ses.account.exceptions.UnauthorizedException;
import com.amalvadkar.ses.account.mapper.AccountMapper;
import com.amalvadkar.ses.account.models.request.ChangePasswordRequest;
import com.amalvadkar.ses.account.models.request.ForgotPasswordRequest;
import com.amalvadkar.ses.account.models.request.SignInRequest;
import com.amalvadkar.ses.account.models.request.SignUpRequest;
import com.amalvadkar.ses.account.models.request.UnlockAccountRequest;
import com.amalvadkar.ses.account.repositories.UserRepo;
import com.amalvadkar.ses.common.exceptions.ResourceAlreadyExistException;
import com.amalvadkar.ses.common.exceptions.ResourceNotFountException;
import com.amalvadkar.ses.common.models.response.CustomResponse;
import com.amalvadkar.ses.common.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.amalvadkar.ses.account.constants.AccountErrConstants.ACCOUNT_IS_LOCKED_ERR_MSG;
import static com.amalvadkar.ses.account.constants.AccountErrConstants.CONFIRM_PASSWORD_UN_MATCHED_ERR_MSG;
import static com.amalvadkar.ses.account.constants.AccountErrConstants.INVALID_USERNAME_OR_PASSWORD_ERR_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.ACCOUNT_UNLOCKED_RES_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.CHECK_EMAIL_FOR_UNLOCK_ACCOUNT_RES_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.LOGGED_IN_SUCCESSFULLY_RES_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.NEW_PASSWORD_CREATED_SUCCESSFULLY_RES_MSG;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String ID = "id";
    private static final String NAME = "name";
    private final AccountMapper accountMapper;
    private final UserRepo userRepo;

    @Transactional
    public CustomResponse signUp(SignUpRequest signUpRequest) {
        checkForEmail(signUpRequest.email());
        UserEntity userEntity = this.accountMapper.toEntity(signUpRequest);
        String tempPassword = PasswordUtils.generateTempPassword();
        userEntity.setTempPassword(tempPassword);
        UserEntity savedUser = userRepo.save(userEntity);
        //TODO send temp password to user email
        return CustomResponse.success(Map.of(ID, savedUser.getId()), CHECK_EMAIL_FOR_UNLOCK_ACCOUNT_RES_MSG);
    }

    @Transactional
    public CustomResponse unlockAccount(UnlockAccountRequest unlockAccountRequest) {
        checkForNewPasswordAndConfirmPassword(unlockAccountRequest.newPassword(), unlockAccountRequest.confirmPassword());
        UserEntity userEntity = userRepo.getUserEntityByEmailAndTempPassword(unlockAccountRequest.email(), unlockAccountRequest.tempPassword());
        userEntity.setTempPassword(null);
        userEntity.setPassword(unlockAccountRequest.newPassword()); // we will hash it
        userEntity.setIsLocked(false);
        this.userRepo.save(userEntity);
        return CustomResponse.success(null, ACCOUNT_UNLOCKED_RES_MSG);
    }

    @Transactional
    public CustomResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        UserEntity userEntity = this.userRepo.findByEmail(forgotPasswordRequest.email()).orElseThrow(() -> new ResourceNotFountException("Email is not exist"));
        userEntity.setTempPassword(PasswordUtils.generateTempPassword());
        userRepo.save(userEntity);
        return CustomResponse.success(null, CHECK_EMAIL_FOR_UNLOCK_ACCOUNT_RES_MSG);
    }

    @Transactional
    public CustomResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        checkForNewPasswordAndConfirmPassword(changePasswordRequest.newPassword(), changePasswordRequest.confirmPassword());
        UserEntity userEntity = userRepo.getUserEntityByEmailAndTempPassword(changePasswordRequest.email(), changePasswordRequest.tempPassword());
        userEntity.setTempPassword(null);
        userEntity.setPassword(changePasswordRequest.newPassword());
        userRepo.save(userEntity);
        return CustomResponse.success(null, NEW_PASSWORD_CREATED_SUCCESSFULLY_RES_MSG);
    }

    public CustomResponse signIn(SignInRequest signInRequest) {
        UserEntity userEntity = userRepo.findByEmailOrThrowUnauthorizedException(signInRequest.email());
        checkForAccountLock(userEntity);
        checkForCredentials(signInRequest.password(), userEntity.getPassword());
        return CustomResponse.success(Map.of(
                    ID, userEntity.getId(),
                    NAME, userEntity.getName()),
                LOGGED_IN_SUCCESSFULLY_RES_MSG);
    }

    private static void checkForCredentials(String requestPassword, String actualPassword) {
        if(notMatched(requestPassword, actualPassword)){
            throw new UnauthorizedException(INVALID_USERNAME_OR_PASSWORD_ERR_MSG);
        }
    }

    private static void checkForAccountLock(UserEntity user) {
        if(accountIsLockedOf(user)){
            throw new AccountLockException(ACCOUNT_IS_LOCKED_ERR_MSG);
        }
    }

    private static Boolean accountIsLockedOf(UserEntity userEntity) {
        return userEntity.getIsLocked();
    }

    private void checkForEmail(String email) {
        if (alreadyExist(email)) {
            throw new ResourceAlreadyExistException(AccountErrConstants.EMAIL_IS_EXIST_ERR_MSG);
        }
    }

    private boolean alreadyExist(String email) {
        return userRepo.existsByEmail(email);
    }

    private static void checkForNewPasswordAndConfirmPassword(String newPassword, String confirmPassword) {
        if (notMatched(newPassword, confirmPassword)) {
            throw new ConfirmPasswordUnMatchedException(CONFIRM_PASSWORD_UN_MATCHED_ERR_MSG);
        }
    }

    private static boolean notMatched(String password, String anotherPassword) {
        return !password.equals(anotherPassword);
    }
}
