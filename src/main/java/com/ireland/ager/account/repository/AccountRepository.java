package com.ireland.ager.account.repository;

import com.ireland.ager.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Class : AccountRepository
 * @Description : 계정 도메인에 대한 레포지토리
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccessToken(String accessToken);

    Optional<Account> findAccountByAccountEmail(String accountEmail);


}