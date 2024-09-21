package com.amalvadkar.ses.account.services;


import com.amalvadkar.ses.account.constants.AccountErrConstants;
import com.amalvadkar.ses.account.entities.UserEntity;
import com.amalvadkar.ses.account.exceptions.ConfirmPasswordUnMatchedException;
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

import static com.amalvadkar.ses.account.constants.AccountErrConstants.CONFIRM_PASSWORD_UN_MATCHED_ERR_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.ACCOUNT_UNLOCKED_RES_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.CHECK_EMAIL_FOR_UNLOCK_ACCOUNT_RES_MSG;
import static com.amalvadkar.ses.account.constants.AccountResConstants.NEW_PASSWORD_CREATED_SUCCESSFULLY_RES_MSG;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String ID = "id";
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
        checkNewPasswordAndConfirmPassword(unlockAccountRequest.newPassword(), unlockAccountRequest.confirmPassword());
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
        checkNewPasswordAndConfirmPassword(changePasswordRequest.newPassword(), changePasswordRequest.confirmPassword());
        UserEntity userEntity = userRepo.getUserEntityByEmailAndTempPassword(changePasswordRequest.email(), changePasswordRequest.tempPassword());
        userEntity.setTempPassword(null);
        userEntity.setPassword(changePasswordRequest.newPassword());
        userRepo.save(userEntity);
        return CustomResponse.success(null, NEW_PASSWORD_CREATED_SUCCESSFULLY_RES_MSG);
    }

    public void signIn(SignInRequest signInRequest) {

    }

    private void checkForEmail(String email) {
        if (alreadyExist(email)) {
            throw new ResourceAlreadyExistException(AccountErrConstants.EMAIL_IS_EXIST_ERR_MSG);
        }
    }

    private boolean alreadyExist(String email) {
        return userRepo.existsByEmail(email);
    }

    private static void checkNewPasswordAndConfirmPassword(String newPassword, String confirmPassword) {
        if (notMatched(newPassword, confirmPassword)) {
            throw new ConfirmPasswordUnMatchedException(CONFIRM_PASSWORD_UN_MATCHED_ERR_MSG);
        }
    }

    private static boolean notMatched(String newPassword, String confirmPassword) {
        return !newPassword.equals(confirmPassword);
    }
}
