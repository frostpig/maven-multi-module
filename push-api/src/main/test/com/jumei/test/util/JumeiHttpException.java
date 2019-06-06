package com.jumei.test.util;

import java.io.IOException;

public class JumeiHttpException extends IOException {
    /**
     * TODO Please Descrip This Field
     */
    private static final long serialVersionUID = -3941706552813708193L;

    private String errorMsg;

    public JumeiHttpException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public JumeiHttpException(Throwable cause) {
        super(cause);
        this.errorMsg = cause.getMessage();
    }

    public JumeiHttpException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }


    
}
