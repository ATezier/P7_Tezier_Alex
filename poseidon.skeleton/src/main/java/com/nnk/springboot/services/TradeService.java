package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() { return tradeRepository.findAll(); }

    public Trade findById(Integer id) {
        return tradeRepository.findById(id).orElseThrow(() -> {throw new IllegalArgumentException("Invalid id :" + id);});
    }

    public boolean valid(Trade trade) {
        boolean isValid = true;
        if (trade.getAccount() == null || trade.getAccount().isEmpty()) {
            isValid = false;
        }
        if (trade.getType() == null || trade.getType().isEmpty()) {
            isValid = false;
        }
        if (trade.getBuyQuantity() == null || trade.getBuyQuantity() < 0) {
            isValid = false;
        }
        return isValid;
    }

    public void add(Trade trade) {
        if (valid(trade)) {
            tradeRepository.save(trade);
        } else {
            throw new IllegalArgumentException("Invalid trade");
        }
    }

    public void update(Integer id, Trade trade) {
        if(valid(trade)) {
            try {
                Trade tradeToUpdate = this.findById(id);
                tradeToUpdate.setAccount(trade.getAccount());
                tradeToUpdate.setType(trade.getType());
                tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
                tradeToUpdate.setSellQuantity(trade.getSellQuantity());
                tradeToUpdate.setBuyPrice(trade.getBuyPrice());
                tradeToUpdate.setSellPrice(trade.getSellPrice());
                tradeToUpdate.setTradeDate(trade.getTradeDate());
                tradeToUpdate.setSecurity(trade.getSecurity());
                tradeToUpdate.setStatus(trade.getStatus());
                tradeToUpdate.setTrader(trade.getTrader());
                tradeToUpdate.setBenchmark(trade.getBenchmark());
                tradeToUpdate.setBook(trade.getBook());
                tradeToUpdate.setCreationName(trade.getCreationName());
                tradeToUpdate.setCreationDate(trade.getCreationDate());
                tradeToUpdate.setRevisionName(trade.getRevisionName());
                tradeToUpdate.setRevisionDate(trade.getRevisionDate());
                tradeToUpdate.setDealName(trade.getDealName());
                tradeToUpdate.setDealType(trade.getDealType());
                tradeToUpdate.setSourceListId(trade.getSourceListId());
                tradeToUpdate.setSide(trade.getSide());
                tradeRepository.save(tradeToUpdate);
            } catch (Exception e) {
                throw new IllegalArgumentException("Trade doesn't exist");
            }
        } else {
            throw new IllegalArgumentException("Invalid trade");
        }
    }

    public void deleteById(Integer id) {
        try {
            Trade tradeToDelete = this.findById(id);
            tradeRepository.delete(tradeToDelete);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid id :" + id);
        }
    }
}
