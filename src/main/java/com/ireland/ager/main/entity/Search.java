package com.ireland.ager.main.entity;

import com.ireland.ager.config.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Class : Product
 * @Description : 상품도메인에 대한 엔티티
 **/
@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "searchId", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Search implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchId;

    private String keyword;

    public Search(String keyword) {
        super();
    }
}