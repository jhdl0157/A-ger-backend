package com.ireland.ager.account.dto.request;

import com.ireland.ager.account.entity.Account;
import lombok.Data;

/**
 * @Class : AccountUpdateRequest
 * @Description : 계정 도메인에 대한 Request DTO
 **/
@Data
public class AccountUpdateRequest {
    private String profileNickname;

    /**
     * @Method : toAccount
     * @Description : 계정 정보 수정 데이터 객체화
     * @Parameter : [updateAccount]
     * @Return : Account
     **/
    public Account toAccount(Account updateAccount) {
        updateAccount.setProfileNickname(this.profileNickname);
        return updateAccount;
    }
}