package com.ireland.ager.account.dto.response;

import com.ireland.ager.account.entity.Account;
import lombok.Data;

/**
 * @Class : KakaoResponse
 * @Description : 카카오 도메인에 대한 Response DTO
 **/
@Data
public class KakaoResponse {

    private Integer id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Data
    public class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;

        @Data
        public class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }

    /**
     * @Method : toAccount
     * @Description : 카카오 계정 정보 데이터 응답 객체화
     * @Parameter : [accessToken, refreshToken]
     * @Return : Account
     **/
    public Account toAccount(String accessToken) {
        Account account = new Account();
        if (this.kakao_account.email == null || this.kakao_account.email.equals(""))
            account.setAccountEmail(String.valueOf(this.id));
        else account.setAccountEmail(this.kakao_account.email);
        account.setProfileNickname(this.properties.nickname);
        account.setUserName(this.kakao_account.profile.nickname);
        account.setProfileImageUrl(this.kakao_account.profile.profile_image_url);
        account.setAccessToken(accessToken);
        return account;
    }
}