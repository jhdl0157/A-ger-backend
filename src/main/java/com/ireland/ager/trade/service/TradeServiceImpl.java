package com.ireland.ager.trade.service;

import com.ireland.ager.account.entity.Account;
import com.ireland.ager.account.exception.UnAuthorizedAccessException;
import com.ireland.ager.account.service.AccountServiceImpl;
import com.ireland.ager.chat.entity.MessageRoom;
import com.ireland.ager.chat.entity.ReviewStatus;
import com.ireland.ager.chat.repository.MessageRoomRepository;
import com.ireland.ager.main.exception.NotFoundException;
import com.ireland.ager.product.entity.Product;
import com.ireland.ager.product.entity.ProductStatus;
import com.ireland.ager.product.repository.ProductRepository;
import com.ireland.ager.trade.entity.Trade;
import com.ireland.ager.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Class : TradeServiceImpl
 * @Description : 거래 도메인에 대한 서비스
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class TradeServiceImpl {

    private final ProductRepository productRepository;
    private final AccountServiceImpl accountService;
    private final MessageRoomRepository messageRoomRepository;
    private final TradeRepository tradeRepository;

    /**
     * @Method : isUpdated
     * @Description : 거래 상태 업데이트
     * @Parameter : [roomId, accessToken, updateStatus]
     * @Return : null
     **/
    public void isUpdated(Long roomId,
                          String accessToken,
                          String updateStatus) {
        MessageRoom messageRoom = messageRoomRepository.findById(roomId).orElseThrow(NotFoundException::new);
        Product product = messageRoom.getProduct();
        Account accountByAccessToken = accountService.findAccountByAccessToken(accessToken);
        if (!product.getAccount().equals(accountByAccessToken)) {
            throw new UnAuthorizedAccessException();
        }
        product.setStatus(ProductStatus.valueOf(updateStatus));
        if (product.getStatus().equals(ProductStatus.COMPLETE)) {
            messageRoom.setReviewStatus(ReviewStatus.SALE);
            messageRoomRepository.save(messageRoom);
        }
        tradeRepository.save(
                Trade.toTrade(
                        productRepository.save(product)
                        , messageRoom.getBuyerId()));
    }
}
