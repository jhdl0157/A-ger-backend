package com.ireland.ager.review.repository;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Class : ReviewRepository
 * @Description : 리뷰 도메인에 대한 레포지토리
 **/
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
