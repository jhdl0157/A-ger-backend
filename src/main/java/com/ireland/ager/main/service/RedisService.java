package com.ireland.ager.main.service;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class : RedisService
 * @Description : 메인 도메인에 대한 레디스 서비스
 **/
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;
    private final AccountServiceImpl accountService;

    /**
     * @Method : getSearchList
     * @Description : 검색 리스트 조회
     * @Parameter : [accessToken]
     * @Return : List<String>
     **/
    public List<String> getSearchList(String accessToken) {
        Account accountByAccessToken = accountService.findAccountByAccessToken(accessToken);
        String key = "search::" + accountByAccessToken.getAccountId();
        ListOperations listOperations = redisTemplate.opsForList();
        return listOperations.range(key, 0, listOperations.size(key));
    }

    /**
     * @Method : postKeyword
     * @Description : 사용자 별 검색 키워드 등록
     * @Parameter : [accessToken, keyword]
     * @Return : null
     **/
    public void postKeyword(String accessToken, String keyword) {
        if (keyword == null) return;
        log.info("{}", keyword);
        Account accountByAccessToken = accountService.findAccountByAccessToken(accessToken);
        String key = "search::" + accountByAccessToken.getAccountId();
        ListOperations listOperations = redisTemplate.opsForList();
        for (Object pastKeyword : listOperations.range(key, 0, listOperations.size(key))) {
            if (String.valueOf(pastKeyword).equals(keyword)) return;
        }
        if (listOperations.size(key) < 5) {
            listOperations.rightPush(key, keyword);
        } else if (listOperations.size(key) == 5) {
            listOperations.leftPop(key);
            listOperations.rightPush(key, keyword);
        }
    }
}
