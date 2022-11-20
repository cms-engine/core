package com.ecommerce.engine.exception;

public class UserPermissionException extends RuntimeException {

    public UserPermissionException() {
        super("You can change only your data!");
    }

}
