package com.space.service;

import com.space.Exeptions.BadRequestException;
import com.space.Exeptions.ShipNotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
