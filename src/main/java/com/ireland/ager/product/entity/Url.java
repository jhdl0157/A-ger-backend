package com.ireland.ager.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Class : Url
 * @Description : URL도메인에 대한 엔티티
 **/
@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "urlId", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Url implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urlId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product productId;

    private String url;

}
