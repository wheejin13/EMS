package com.haemilsoft.ems.network.asynctask;

public enum ResultCode {
    SUCCESS(200, "SUCCESS"),
    FAIL(400, "FAIL"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT FOUND"),
    USER_NOT_FOUND(414, "USER NOT FOUND"),
    USER_EMPTY(415, "USER EMPTY"),
    PASSWORD_EMPTY(416, "PASSWORD EMPTY"),
    PASSWORD_NOT_MATCHED(417, "PASSWORD NOT MATCHED"),
    PASSWORD_MIN_LENGTH(418, "PASSWORD MIN LENGTH"),
    INCORRECT_BASE64_CHAR(419, "INCORRECT BASE64 CHAR"),
    ENC_TYPE_MISMATCH(420, "ENC TYPE MISMATCH"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    CONNECTION_FAILED(510, "CONNECTION FAILED");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public static String getMessage(int code) {

        switch (code) {
            case 200:
                return ResultCode.SUCCESS.message();
            case 401:
                return ResultCode.UNAUTHORIZED.message();
            case 404:
                return ResultCode.NOT_FOUND.message();
            case 414:
                return ResultCode.USER_NOT_FOUND.message();
            case 415:
                return ResultCode.USER_EMPTY.message();
            case 416:
                return ResultCode.PASSWORD_EMPTY.message();
            case 417:
                return ResultCode.PASSWORD_NOT_MATCHED.message();
            case 418:
                return ResultCode.PASSWORD_MIN_LENGTH.message();
            case 419:
                return ResultCode.INCORRECT_BASE64_CHAR.message();
            case 420:
                return ResultCode.ENC_TYPE_MISMATCH.message();
            case 500:
                return ResultCode.INTERNAL_SERVER_ERROR.message();
            case 510:
                return ResultCode.CONNECTION_FAILED.message();
            default:
                return ResultCode.FAIL.message();
        }
    }

    public static ResultCode getInst(int code) {

        switch (code) {
            case 200:
                return ResultCode.SUCCESS;
            case 401:
                return ResultCode.UNAUTHORIZED;
            case 404:
                return ResultCode.NOT_FOUND;
            case 414:
                return ResultCode.USER_NOT_FOUND;
            case 415:
                return ResultCode.USER_EMPTY;
            case 416:
                return ResultCode.PASSWORD_EMPTY;
            case 417:
                return ResultCode.PASSWORD_NOT_MATCHED;
            case 418:
                return ResultCode.PASSWORD_MIN_LENGTH;
            case 419:
                return ResultCode.INCORRECT_BASE64_CHAR;
            case 420:
                return ResultCode.ENC_TYPE_MISMATCH;
            case 500:
                return ResultCode.INTERNAL_SERVER_ERROR;
            case 510:
                return ResultCode.CONNECTION_FAILED;
            default:
                return ResultCode.FAIL;
        }
    }
}
