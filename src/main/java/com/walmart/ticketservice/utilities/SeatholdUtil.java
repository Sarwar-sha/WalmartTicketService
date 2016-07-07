package com.walmart.ticketservice.utilities;

import java.util.List;

import com.walmart.tikectservice.domain.Seathold;

public final class SeatholdUtil {
	
	public static Seathold findhightPrioritySeat(List<Seathold> seatList){
		
		Seathold seat = new Seathold();
	   seat = seatList.get(0);
		for(Seathold seathold: seatList){
			if(seat.getPriority() < seathold.getPriority()){
	         seat = seathold;			
			}
		}
		
		return seat;
	}
	

	public static Seathold findSeatBeteenMinMax(List<Seathold> seatList, Integer minLevel, Integer maxLevel){
		Seathold seat = new Seathold();
		seat = seatList.get(0);
		boolean flag = false;
		for(Seathold seathold: seatList){
			if(minLevel <= seathold.getLevelId() && seathold.getLevelId()<= maxLevel && seat.getPriority() <= seathold.getPriority()){
			 seat = seathold;
			 flag = true;
			}
		}
		if(flag){
		return seat;
		}else {
			Seathold empty = new Seathold();
			return empty;
		}
	}

}
