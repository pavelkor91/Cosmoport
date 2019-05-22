package com.space.service;

import com.space.Exeptions.BadRequestException;
import com.space.Exeptions.ShipNotFoundException;
import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ShipServiceImpl implements ShipService{

    private ShipRepository shipRepository;
    private static final Validator VALIDATOR = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();


    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    private void idValidation(Long id){
        if(id == null || id == 0)
            throw new BadRequestException();

        if(shipRepository.existsById(id) == false)
            throw new ShipNotFoundException();
    }

    private Double getRation(Ship ship){
        Calendar cal = Calendar.getInstance();
        cal.setTime(ship.getProdDate());
        int year = cal.get(Calendar.YEAR);
        BigDecimal raiting = new BigDecimal((80 * ship.getSpeed() * (ship.getUsed() ? 0.5 : 1)) / (3019 - year + 1));
        raiting = raiting.setScale(2, RoundingMode.HALF_UP);
        return raiting.doubleValue();
    }
    @Override
    public Ship getShip(Long id) {
        idValidation(id);
        return shipRepository.findById(id).get();
    }

    @Override
    public void deleteShip(Long id) {
        idValidation(id);
        shipRepository.deleteById(id);
    }


    @Override
    public Ship addShip(Ship newShip) {
       Set<ConstraintViolation<Ship>> constraintViolations = VALIDATOR.validate(newShip);
       if (constraintViolations.size() > 0) {
           throw new BadRequestException();
       }

        if (newShip.getUsed() == null)
            newShip.setUsed(false);

        Double raiting = getRation(newShip);
        newShip.setRating(raiting);
        return shipRepository.saveAndFlush(newShip);
    }

    @Override
    public List<Ship> getAllShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed,
                                  Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating,
                                  ShipOrder order) {
        Date afterDate = after == null ? null : new Date(after);
        Date beforeDate = before == null ? null : new Date(before);
        List<Ship> allShips = shipRepository.findAll();
        Iterator iterator = allShips.iterator();
        //TODO remake by criteria api specification
        while (iterator.hasNext()){
            Ship tmp = (Ship)iterator.next();
            if(name != null && !tmp.getName().toLowerCase().contains(name.toLowerCase())) {iterator.remove(); continue;}
            if(planet != null && !tmp.getPlanet().toLowerCase().contains(planet.toLowerCase())) {iterator.remove(); continue;}
            if(shipType != null && tmp.getShipType() != shipType) {iterator.remove(); continue;}
            if(beforeDate != null && tmp.getProdDate().after(beforeDate)) {iterator.remove(); continue;}
            if(afterDate != null && tmp.getProdDate().before(afterDate)) {iterator.remove(); continue;}
            if(minCrewSize != null && tmp.getCrewSize() < minCrewSize) {iterator.remove(); continue;}
            if(maxCrewSize != null && tmp.getCrewSize() > maxCrewSize) {iterator.remove(); continue;}
            if(isUsed != null && tmp.getUsed().booleanValue() != isUsed.booleanValue()) {iterator.remove(); continue;}
            if(minSpeed != null && tmp.getSpeed() < minSpeed) {iterator.remove(); continue;}
            if(maxSpeed != null && tmp.getSpeed() > maxSpeed) {iterator.remove(); continue;}
            if(minRating != null && tmp.getRating() < minRating) {iterator.remove(); continue;}
            if(maxRating != null && tmp.getRating() > maxRating) {iterator.remove(); continue;}
        }

        if(order != null)
        Collections.sort(allShips, new Comparator<Ship>() {
            @Override
            public int compare(Ship o1, Ship o2) {
                switch (order){
                    case SPEED: return o1.getSpeed().compareTo(o2.getSpeed());
                    case ID: return o1.getId().compareTo(o2.getId());
                    case DATE: return o1.getProdDate().compareTo(o2.getProdDate());
                    case RATING: return o1.getRating().compareTo(o2.getRating());
                    default: return 0;
                }
            }
        });

        return allShips;
    }

    @Override
    public List<Ship> pagination(List<Ship> ships, Integer pageNumber, Integer pageSize) {
        int first = pageNumber * pageSize;
        int last = first + pageSize > ships.size() ? ships.size() : first + pageSize;

        return ships.subList(first, last);
    }


    @Override
    public Integer getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order) {
         return getAllShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating, order).size();
    }

    @Override
    public Ship updateShip(long id, Ship ship) {
        idValidation(id);
        Set<ConstraintViolation<Ship>> constraintViolations = VALIDATOR.validate(ship);
        if (constraintViolations.size() > 0) {
            for(ConstraintViolation<Ship> constraintViolation: constraintViolations){
                if(constraintViolation.getMessage().equals("Null")){
                    continue;
                }
                else
                    throw new BadRequestException();
            }
        }
        BeanUtilsBean notNull = new NullAwareBeanUtilsBean();

        Ship dbShip = shipRepository.findById(id).get();
        try {
            notNull.copyProperties(dbShip, ship);
        }
        catch (IllegalAccessException | InvocationTargetException e){
        }
        Double rate = getRation(dbShip);
        dbShip.setRating(rate);
        dbShip.setId(id);

        return shipRepository.save(dbShip);
    }

}
