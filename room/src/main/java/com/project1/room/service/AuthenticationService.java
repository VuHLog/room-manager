package com.project1.room.service;

import com.nimbusds.jose.JOSEException;
import com.project1.room.dto.request.AuthenticationRequest;
import com.project1.room.dto.request.IntrospectRequest;
import com.project1.room.dto.request.LogoutRequest;
import com.project1.room.dto.request.RefreshRequest;
import com.project1.room.dto.response.AuthenticationResponse;
import com.project1.room.dto.response.IntrospectResponse;
import com.project1.room.entity.Users;

import java.text.ParseException;

public interface AuthenticationService {

    //verify token
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    //check username, password -> generate token
    AuthenticationResponse authenticate(AuthenticationRequest request);

    public String generateToken(Users user);

    public void logout(LogoutRequest request) throws JOSEException, ParseException;

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
