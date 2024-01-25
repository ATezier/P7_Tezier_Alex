package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;


public interface BidListRepository extends JpaRepository<BidList, Integer> {
    public List<BidList> findAll();
    public BidList save(BidList bidList);
    public BidList findByBidListId(@NonNull Integer bidListId);
    public void delete(BidList bidList);

    public BidList findByAccountAndDealTypeAndBidQuantity(@NonNull String account, @NonNull String dealType, @NonNull Double bidQuantity);
}
