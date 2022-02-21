package com.ireland.ager.account.service;

import com.ireland.ager.account.dto.request.AccountUpdateRequest;
import com.ireland.ager.account.dto.response.MyAccountResponse;
import com.ireland.ager.account.dto.response.OtherAccountResponse;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.exception.UnAuthorizedAccessException;
import com.ireland.ager.account.repository.AccountRepository;
import com.ireland.ager.main.exception.NotFoundException;
import com.ireland.ager.main.service.UploadServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Class : AccountServiceImpl
 * @Description : 계정 도메인에 대한 서비스
 **/
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl {
    private final AccountRepository accountRepository;
    private final RedisTemplate redisTemplate;
    private final UploadServiceImpl uploadService;

    /**
     * @Method : findAccountByAccountEmail
     * @Description : 이메일로 계정 조회
     * @Parameter : [accountEmail]
     * @Return : Account
     **/
    public Account findAccountByAccountEmail(String accountEmail) {
        return accountRepository.findAccountByAccountEmail(accountEmail).orElse(null);
    }

    /**
     * @Method : findAccountByAccessToken
     * @Description : 액세스 토큰으로 계정 조회
     * @Parameter : [accessToken]
     * @Return : Account
     **/
    @Cacheable("account")
    public Account findAccountByAccessToken(String accessToken) {
        return accountRepository.findAccountByAccessToken(accessToken).orElseThrow(NotFoundException::new);
    }

    /**
     * @Method : insertAccount
     * @Description : 회원 가입
     * @Parameter : [newAccount]
     * @Return : MyAccountResponse
     **/
    public MyAccountResponse insertAccount(Account newAccount) {
        Account saveAccount = accountRepository.save(newAccount);
        return MyAccountResponse.toAccountResponse(saveAccount);
    }

    /**
     * @Method : updateAccount
     * @Description : 계정 정보 수정
     * @Parameter : [accessToken, accountId, accountUpdateRequest, multipartFile]
     * @Return : MyAccountResponse
     **/
    public MyAccountResponse updateAccount(String accessToken, Long accountId,
                                           AccountUpdateRequest accountUpdateRequest,
                                           MultipartFile multipartFile) throws IOException {
        Account optionalUpdateAccount = findAccountByAccessToken(accessToken);
        if (!(optionalUpdateAccount.getAccountId().equals(accountId))) {
            throw new UnAuthorizedAccessException();
        }
        Account updatedAccount = accountUpdateRequest.toAccount(optionalUpdateAccount);

        if (!(multipartFile.isEmpty())) {
            String uploadImg = uploadService.uploadImg(multipartFile);
            updatedAccount.setProfileImageUrl(uploadImg);
        }
        accountRepository.save(updatedAccount);
        return MyAccountResponse.toAccountResponse(updatedAccount);
    }

    /**
     * @Method : deleteAccount
     * @Description : 계정 삭제
     * @Parameter : [accessToken, accountId]
     * @Return : null
     **/
    public void deleteAccount(String accessToken, Long accountId) {
        Account accountByAccessToken = findAccountByAccessToken(accessToken);
        if (!(accountByAccessToken.getAccountId().equals(accountId))) {
            throw new UnAuthorizedAccessException();
        }
        String key = "search::" + accountId;
        redisTemplate.delete(key);
        accountRepository.deleteById(accountId);
    }
}

