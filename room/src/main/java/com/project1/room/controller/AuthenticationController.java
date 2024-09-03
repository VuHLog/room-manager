package com.project1.room.controller;

import com.nimbusds.jose.JOSEException;
import com.project1.room.dto.request.AuthenticationRequest;
import com.project1.room.dto.request.IntrospectRequest;
import com.project1.room.dto.request.LogoutRequest;
import com.project1.room.dto.request.RefreshRequest;
import com.project1.room.dto.response.ApiResponse;
import com.project1.room.dto.response.AuthenticationResponse;
import com.project1.room.dto.response.IntrospectResponse;
import com.project1.room.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var res = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(res)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var res = authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(res)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var res = authenticationService.refreshToken(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(res)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> authenticate(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);

        return ApiResponse.<Void>builder()
                .build();
    }
}
