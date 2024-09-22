package com.amalvadkar.ses.account.controllers;

import com.amalvadkar.ses.account.models.request.ChangePasswordRequest;
import com.amalvadkar.ses.account.models.request.ForgotPasswordRequest;
import com.amalvadkar.ses.account.models.request.SignInRequest;
import com.amalvadkar.ses.account.models.request.SignUpRequest;
import com.amalvadkar.ses.account.models.request.UnlockAccountRequest;
import com.amalvadkar.ses.account.services.UserService;
import com.amalvadkar.ses.common.models.response.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ses/account")
@RequiredArgsConstructor
public class AccountController {
    private static final String ENDPOINT_SIGN_UP = "/sign-up";
    private static final String ENDPOINT_UNLOCK_ACCOUNT = "/unlock-account";
    private static final String ENDPOINT_FORGOT_PASSWORD = "/forgot-password";
    private static final String ENDPOINT_CHANGE_PASSWORD = "/change-password";
    private static final String ENDPOINT_SIGN_IN = "/sign-in";

    private final UserService userService;

    @PostMapping(ENDPOINT_SIGN_UP)
    public ResponseEntity<CustomResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(this.userService.signUp(signUpRequest));
    }

    @PostMapping(ENDPOINT_UNLOCK_ACCOUNT)
    public ResponseEntity<CustomResponse> unlockAccount(@Valid @RequestBody UnlockAccountRequest unlockAccountRequest) {
        return ResponseEntity.ok(this.userService.unlockAccount(unlockAccountRequest));
    }

    @PostMapping(ENDPOINT_FORGOT_PASSWORD)
    public CustomResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return this.userService.forgotPassword(forgotPasswordRequest);
    }

    @PostMapping(ENDPOINT_CHANGE_PASSWORD)
    public ResponseEntity<CustomResponse>changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(this.userService.changePassword(changePasswordRequest));
    }


    @PostMapping(ENDPOINT_SIGN_IN)
    public CustomResponse signIn(@Valid @RequestBody SignInRequest signInRequest){
      return this.userService.signIn(signInRequest);
    }
}
