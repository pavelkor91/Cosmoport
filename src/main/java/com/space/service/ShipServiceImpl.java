package com.space.service;

import com.space.Exeptions.BadRequestException;
import com.space.Exeptions.ShipNotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Set;

@Service
public class ShipServiceImpl implements ShipService{

    private ShipRepository shipRepository;


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
    public Integer getShipsCount() {
        return null;
    }

    @Override
    public Ship addShip(Ship newShip) {
        Validator validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
       Set<ConstraintViolation<Ship>> constraintViolations = validator.validate(newShip);
       if (constraintViolations.size() > 0) {
           throw new BadRequestException();
       }

        if (newShip.getUsed() == null)
            newShip.setUsed(false);

        Double raiting = getRation(newShip);
        newShip.setRating(raiting);
        return shipRepository.saveAndFlush(newShip);
    }
}
