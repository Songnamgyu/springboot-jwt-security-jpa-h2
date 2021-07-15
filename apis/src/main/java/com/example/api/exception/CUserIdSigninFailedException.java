package com.example.api.exception;

public class CUserIdSigninFailedException extends RuntimeException {
    public CUserIdSigninFailedException(String msg, Throwable t) {
        super("회원가입에 실패하였습니다.", t);
    }

    public CUserIdSigninFailedException(String msg) {
        super("회원가입에 실패하였습니다.");
    }

    public CUserIdSigninFailedException() {
        super();
    }

}
