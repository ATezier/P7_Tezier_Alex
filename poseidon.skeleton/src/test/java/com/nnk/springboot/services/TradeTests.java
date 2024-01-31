package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeTests {
    @MockBean
    TradeRepository tradeRepository;
    @Autowired
    TradeService tradeService;

    @Test
    public void tradeTest() {
        Trade trade = new Trade("Trade Account", "Type", 10d);
        Integer id = 1;
        trade.setTradeId(id);
        List<Trade> trades = new ArrayList<Trade>();
        trades.add(trade);

        // Valid
        Assertions.assertTrue(tradeService.valid(trade));

        // Create
        given(tradeRepository.save(trade)).willReturn(trade);
        Assertions.assertNotNull(tradeService.create(trade));

        // Read
        given(tradeRepository.findAll()).willReturn(trades);
        Assertions.assertTrue(tradeService.findAll().size() > 0);

        given(tradeRepository.findById(id)).willReturn(Optional.of(trade));
        Assertions.assertNotNull(tradeService.findById(id));


        // Update
        Assertions.assertNotNull(tradeService.update(id, new Trade(trade.getAccount(), "TYPE", trade.getBuyQuantity())));

        // Delete
        Assertions.assertDoesNotThrow( () -> tradeService.deleteById(id));
    }
}
