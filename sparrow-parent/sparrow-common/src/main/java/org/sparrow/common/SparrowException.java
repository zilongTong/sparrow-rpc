//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.sparrow.common;
/**
 * @author leo
 * @date 2019/2/12
 */
public class SparrowException extends RuntimeException {
    private static final long serialVersionUID = 5402012883466443408L;
    private String code;
    private String message;

    public SparrowException() {
    }

    public SparrowException(String code) {
        this(code, (String)null);
    }

    public SparrowException(String code, String message) {
        this(code, message, (Throwable)null);
    }

    public SparrowException(String code, Throwable cause) {
        this(code, (String)null, cause);
    }

    public SparrowException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
