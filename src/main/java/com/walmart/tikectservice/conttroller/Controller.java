package com.walmart.tikectservice.conttroller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.tikectservice.seatdao.TicketService;
import com.walmart.tikectservice.domain.Seathold;

@RestController
public class Controller {

    @Resource(name = "TicketServiceImpl")
    private TicketService ticketServiceDao;

    @RequestMapping(value = "/numSeatsAvailable", method = RequestMethod.GET)
    public int countAvailableSeat(@RequestParam(value = "levelNum", required = false) Optional<Integer> levelNum) {
    	try{
       return ticketServiceDao.numSeatsAvailable(levelNum);
    	} catch (EmptyResultDataAccessException ex) {
            return 0;
        }
    }
    
    @RequestMapping(value = "/findAndHoldSeats", method = RequestMethod.GET)
    public Seathold findAndHoldSeats(@RequestParam(value = "numSeats", required = true) Integer numSeats,
    		@RequestParam(value = "customerEmail", required = true) String customerEmail,
    		@RequestParam(value = "minLevel", required = false) Optional<Integer> minLevel,
    		@RequestParam(value = "maxLevel", required = false) Optional<Integer> maxLevel) {
    	try{
       return ticketServiceDao.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
    	} catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @RequestMapping(value = "/reserveSeats", method = RequestMethod.GET)
    public String reserveSeats(@RequestParam(value = "numSeats", required = true) Integer numSeats,
    		@RequestParam(value = "customerEmail", required = true) String customerEmail) {
    	try{
       return ticketServiceDao.reserveSeats(numSeats, customerEmail);
    	} catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
