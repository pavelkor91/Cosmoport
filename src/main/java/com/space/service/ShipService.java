package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.List;

public interface ShipService {

    Ship getShip(Long id);
    void deleteShip(Long id);
    Integer getShipsCount( String name,
                           String planet,
                           ShipType shipType,
                           Long after,
                           Long before,
                           Boolean isUsed,
                           Double minSpeed,
                           Double maxSpeed,
                           Integer minCrewSize,
                           Integer maxCrewSize,
                           Double minRating,
                           Double maxRating,
                           ShipOrder order
    );
    Ship addShip(Ship newShip);
    Ship updateShip(long id, Ship ship);
    List<Ship> getAllShips(
            String name,
            String planet,
            ShipType shipType,
            Long after,
            Long before,
            Boolean isUsed,
            Double minSpeed,
            Double maxSpeed,
            Integer minCrewSize,
            Integer maxCrewSize,
            Double minRating,
            Double maxRating,
            ShipOrder order
    );
    List<Ship> pagination(List<Ship> ships, Integer pageNumber, Integer pageSize);
}
