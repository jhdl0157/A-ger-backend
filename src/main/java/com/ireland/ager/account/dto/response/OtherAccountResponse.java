package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Class : OtherAccountResponse
 * @Description : 계정 도메인에 대한 Response DTO
 **/
@Data
@Builder
public class OtherAccountResponse {
    Long accountId;
    String accountEmail;
    String profileNickname;
    String profileImageUrl;
    Double avgStar;
    LocalDateTime createdAt;

    /**
     * @Method : toOtherAccountResponse
     * @Description : 계정 정보 데이터 응답 객체화
     * @Parameter : [account]
     * @Return : OtherAccountResponse
     **/
    public static OtherAccountResponse toOtherAccountResponse(Account account) {
        return OtherAccountResponse.builder()
                .profileNickname(account.getProfileNickname())
                .accountEmail(account.getAccountEmail())
                .profileImageUrl(account.getProfileImageUrl())
                .accountId(account.getAccountId())
                .createdAt(account.getCreatedAt())
                .avgStar(account.getAvgStar())
                .build();
    }
}
