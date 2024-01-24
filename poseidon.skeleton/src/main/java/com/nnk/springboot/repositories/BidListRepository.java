package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

    BidList save(BidList bidList);
    BidList findByBidListId(@NonNull Integer bidListId);

    void delete(BidList bidList);

    boolean updateByBidListId(@NonNull Integer bidListId, BidList bidList);

}
