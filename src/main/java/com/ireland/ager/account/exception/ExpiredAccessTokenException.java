package com.ireland.ager.account.exception;

/**
 * @Class : ExpiredAccessTokenException
 * @Description : 액세스 토큰 만료 에러 처리
 **/
public class ExpiredAccessTokenException extends RuntimeException {
    public ExpiredAccessTokenException() {
        super();
    }
}
