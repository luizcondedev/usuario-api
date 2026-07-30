package com.luizconde.usuario.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String message) {
        super(message);
    }

}
