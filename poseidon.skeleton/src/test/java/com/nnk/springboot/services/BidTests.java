package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BidTests {
    @MockBean
    BidListRepository bidListRepository;
    @Autowired
    BidListService bidListService;

    @Test
    public void bidListTest() {
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        bid.setBidListId(1);
        List<BidList> bidList = new ArrayList<BidList>();
        bidList.add(bid);

        // Valid
        Assertions.assertTrue(bidListService.valid(bid));

        // Create
        given(bidListRepository.findByAccountAndTypeAndBidQuantity(
                bid.getAccount(), bid.getType(), bid.getBidQuantity())).willReturn(null);
        given(bidListRepository.save(bid)).willReturn(bid);
        bid = bidListService.create(bid);
        Assertions.assertNotNull(bid);

        // Read
        given(bidListRepository.findAll()).willReturn(bidList);
        Assertions.assertTrue(bidListService.findAll().size() > 0);

        given(bidListRepository.findById(bid.getBidListId())).willReturn(Optional.of(bid));
        Assertions.assertNotNull(bidListService.findById(bid.getBidListId()));


        // Update
        Assertions.assertNotNull(bidListService.update(bid.getBidListId(), new BidList(
                bid.getAccount(), bid.getType(), 20d)));

        // Delete
        Integer id = bid.getBidListId();
        Assertions.assertDoesNotThrow( () -> bidListService.deleteById(id));
    }
}
