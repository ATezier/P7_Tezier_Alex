package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidListService {
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public BidList findById(Integer id) {
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    public void deleteById(Integer id) {
        try {
            BidList bidList = this.findById(id);
            bidListRepository.delete(bidList);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Id");
        }
    }

    public boolean valid(BidList bidList) {
        boolean isValid = true;
        if (bidList.getAccount() == null || bidList.getAccount().isEmpty()) {
            isValid = false;
        }
        if (bidList.getDealType() == null || bidList.getDealType().isEmpty()) {
            isValid = false;
        }
        if (bidList.getBidQuantity() == null) {
            isValid = false;
        }
        return isValid;
    }

    public void addBidList(BidList bidList) {
        if (valid(bidList)) {
            if (bidListRepository.findByAccountAndDealTypeAndBidQuantity(bidList.getAccount(), bidList.getDealType(), bidList.getBidQuantity()) == null) {
                bidList.setCreationDate(new Timestamp(System.currentTimeMillis()));
                bidListRepository.save(bidList);
            } else {
                throw new IllegalArgumentException("BidList already exists");
            }
        } else {
            throw new IllegalArgumentException("Invalid BidList");
        }
    }

    public void update(Integer id, BidList bidList) {
        if (valid(bidList)) {
            try {
                BidList bidListToUpdate = this.findById(id);
                if(bidList.getBidQuantity() == bidListToUpdate.getBidQuantity()
                        && bidList.getAccount().equals(bidListToUpdate.getAccount())
                        && bidList.getDealType().equals(bidListToUpdate.getDealType())) {
                    throw new IllegalArgumentException("BidList up to date");
                } else {
                    bidListToUpdate.setAccount(bidList.getAccount());
                    bidListToUpdate.setDealType(bidList.getDealType());
                    bidListToUpdate.setBidQuantity(bidList.getBidQuantity());
                    bidListRepository.save(bidListToUpdate);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("BidList does not exist");
            }
        } else {
            throw new IllegalArgumentException("Invalid BidList");
        }
    }
}