package com.jwt.boilerplate.demo.payload.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

    private final String token;
}
