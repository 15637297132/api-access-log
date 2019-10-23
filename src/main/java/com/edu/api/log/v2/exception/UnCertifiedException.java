package com.edu.api.log.v2.exception;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-10-09 13:49
 **/
public class UnCertifiedException extends RuntimeException {

    /**
     * Constructs an {@code ArithmeticException} with no detail
     * message.
     */
    public UnCertifiedException() {
        super();
    }

    /**
     * Constructs an {@code ArithmeticException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public UnCertifiedException(String s) {
        super(s);
    }
}
