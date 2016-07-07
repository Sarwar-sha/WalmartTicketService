package com.walmart.tikectservice.seatdao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.ticketservice.utilities.SeatholdUtil;
import com.walmart.tikectservice.domain.Seathold;

@Repository("TicketServiceImpl")
@Transactional
public class TicketServiceImpl implements TicketService {

	private final static String AVAILABLE = "AVAILABLE";
	private final static String HOLD = "HOLD";
	private final static String RESERVE = "RESERVE";
	private final static String TAG = "TicketServiceImpl";
	private final static int SECONDS = 2 * 60;
	private static Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		try {
			update();
			if (venueLevel.isPresent()) {
				return entityManager.createQuery("from Seathold s where s.levelId = :venueLevel and s.status = :status")
						.setParameter("venueLevel", venueLevel.get()).setParameter("status", AVAILABLE).getResultList()
						.size();
			} else {
				return entityManager.createQuery("from Seathold s where s.status = :status")
						.setParameter("status", AVAILABLE).getResultList().size();
			}
		} catch (NoResultException e) {
			log.info(TAG + e.toString());
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Seathold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, SECONDS);

		Seathold seat = new Seathold();
		List<Seathold> seatList = new ArrayList<Seathold>();

		try {
			update();
			if (minLevel.isPresent() && maxLevel.isPresent()) {
				seatList = entityManager
						.createQuery("from Seathold s where s.status = :status and s.emailAddress = :email")
						.setParameter("status", AVAILABLE).setParameter("email", customerEmail).getResultList();
				if (!seatList.isEmpty()) {
					seat = SeatholdUtil.findSeatBeteenMinMax(seatList, minLevel.get(), maxLevel.get());
				}
			} else {
				seatList = entityManager
						.createQuery("from Seathold s where s.status = :status and s.emailAddress = :email")
						.setParameter("status", AVAILABLE).setParameter("email", customerEmail).getResultList();
				if (!seatList.isEmpty()) {
					seat = SeatholdUtil.findhightPrioritySeat(seatList);
				}
			}

		} catch (NoResultException e) {
			return seat;
		}
		if (seat.getLevelId() != null) {
			seat.setModified(cal.getTime());
			seat.setStatus(HOLD);
			entityManager.merge(seat);
		}
		return seat;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		
		List<Seathold> seatList = new ArrayList<Seathold>();
		StringBuilder build = new StringBuilder();
		try{
			update();
			seatList = entityManager
					.createQuery(
							"from Seathold s where s.status = :status and s.seatsInRow = :seatsInRow and s.emailAddress = :email")
					.setParameter("status", HOLD).setParameter("seatsInRow", seatHoldId)
					.setParameter("email", customerEmail).getResultList();
			System.out.println(seatList.toString());
		
			for (Seathold seat : seatList) {
				if (seat.getStatus().equals(HOLD)) {
					seat.setStatus(RESERVE);
					entityManager.merge(seat);
					build.append(seat.toString());
				}
			}
		}catch(NoResultException e){
			return "";
		}
		return build.toString();
	}

	@SuppressWarnings("unchecked")
	private void update() {
		List<Seathold> seats = new ArrayList<Seathold>();
		try{
		seats = entityManager.createQuery("from Seathold s where s.modified < current_timestamp and s.status = :status")
				.setParameter("status", HOLD).getResultList();
		if (!seats.isEmpty()) {
			for (Seathold seat : seats) {
				seat.setModified(null);
				seat.setEmailAddress(null);
				seat.setPhoneNumber(null);
				seat.setStatus(AVAILABLE);
				entityManager.merge(seat);
			}
		}
		}catch(NoResultException e){
			log.info(TAG + "non rows is update ");
		}

	}

}
