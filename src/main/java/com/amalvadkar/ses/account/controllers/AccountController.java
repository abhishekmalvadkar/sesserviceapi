package com.amalvadkar.ses.account.controllers;

import com.amalvadkar.ses.account.models.request.CreatePasswordRequest;
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
    private static final String ENDPOINT_CREATE_PASSWORD = "/create-password";
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

    @PostMapping(ENDPOINT_CREATE_PASSWORD)
    public ResponseEntity<CustomResponse> createPassword(@Valid @RequestBody CreatePasswordRequest createPasswordRequest) {
        return ResponseEntity.ok(this.userService.createPassword(createPasswordRequest));
    }


    @PostMapping("/sign-in")
    public void signIn(@Valid @RequestBody SignInRequest signInRequest){
        this.userService.signIn(signInRequest);
    }
}