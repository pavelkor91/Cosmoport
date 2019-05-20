package com.space.service;

import com.space.model.Ship;

public interface ShipService {

    Ship getShip(Long id);
    void deleteShip(Long id);
    Integer getShipsCount();
    Ship addShip(Ship newShip);
    Ship updateShip(long id, Ship ship);

}
