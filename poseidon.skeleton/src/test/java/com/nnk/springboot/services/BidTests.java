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
        Integer id = 1;
        bid.setBidListId(id);
        List<BidList> bidList = new ArrayList<BidList>();
        bidList.add(bid);

        // Valid
        Assertions.assertTrue(bidListService.valid(bid));

        // Create
        given(bidListRepository.save(bid)).willReturn(bid);
        Assertions.assertNotNull(bidListService.create(bid));

        // Read
        given(bidListRepository.findAll()).willReturn(bidList);
        Assertions.assertTrue(bidListService.findAll().size() > 0);

        given(bidListRepository.findById(id)).willReturn(Optional.of(bid));
        Assertions.assertNotNull(bidListService.findById(id));


        // Update
        Assertions.assertNotNull(bidListService.update(id, new BidList(
                bid.getAccount(), bid.getType(), 20d)));

        // Delete
        Assertions.assertDoesNotThrow( () -> bidListService.deleteById(id));
    }
}
