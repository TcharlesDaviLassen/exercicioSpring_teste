package com.example.exercicio.errorsUtils.BusinessException;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private String[] args;
    private boolean forceParse;

    public BusinessException() {
    }

    public BusinessException(final String message, final String... args) {
        super(formatMessage(message, args));
        // super(message);
        this.args = args;
        // setArgs(args);
    }

    private static String formatMessage(String messageKey, String... args) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
        String message = resourceBundle.getString(messageKey);

        return MessageFormat.format(message, args);
    }

    public BusinessException(boolean forceParse, String messageKey, String... args) {
        super(formatMessage(messageKey, args));
        this.args = args;
        this.forceParse = forceParse;
    }

    // public BusinessException(final boolean forceParse, final String message,
    // final String... args) {
    // super(message);
    // this.args = args;
    // this.forceParse = forceParse;
    // }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public boolean hasArgs() {
        return args != null && args.length > 0;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public boolean isForceParse() {
        return forceParse;
    }

    public void setForceParse(boolean forceParse) {
        this.forceParse = forceParse;
    }
}
