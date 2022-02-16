package com.ireland.ager.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ireland.ager.account.entity.Account;
import com.ireland.ager.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : Comment
 * @Description : 댓글 도메인에 대한 엔티티
 **/
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board boardId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account accountId;

    private String commentContent;

    /**
     * @Method : addAccount
     * @Description : 계정 정보 추가
     * @Parameter : [account]
     * @Return : null
     **/
    public void addAccount(Account account) {
        this.accountId = account;
    }
}
