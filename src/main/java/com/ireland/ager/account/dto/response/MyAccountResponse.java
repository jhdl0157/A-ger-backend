package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Class : MyAccountResponse
 * @Description : 계정 도메인에 대한 Response DTO
 **/
@Data
@Builder
public class MyAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String userName;
    String profileImageUrl;
    String accessToken;
    Double avgStar;
    LocalDateTime createdAt;

    /**
     * @Method : toAccountResponse
     * @Description : 계정 정보 데이터 응답 객체화
     * @Parameter : [account]
     * @Return : MyAccountResponse
     **/
    public static MyAccountResponse toAccountResponse(Account account) {
        return MyAccountResponse.builder()
                .accessToken(account.getAccessToken())
                .profileNickname(account.getProfileNickname())
                .userName(account.getUserName())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .avgStar(account.getAvgStar())
                .createdAt(account.getCreatedAt())
                .build();
    }
}