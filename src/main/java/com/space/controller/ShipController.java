package com.space.controller;

import com.space.Exeptions.BadRequestException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/rest")
public class ShipController {
    private ShipService service;

    @Autowired
    public void setService(ShipService service) {
        this.service = service;
    }

    @GetMapping(value = "/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship getShip(@PathVariable(value = "id") String id){
        Long idl;
        try {
            idl = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            throw new BadRequestException();
        }
        return service.getShip(idl);
    }

    @DeleteMapping(value = "/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShip(@PathVariable(value = "id") String id){
        Long idl;
        try {
            idl = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            throw new BadRequestException();
        }
        service.deleteShip(idl);
    }

    @PostMapping(value = "/ships")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship addShip(@RequestBody Ship ship) {

        return service.addShip(ship);

    }
    @PostMapping(value = "/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship editShip(@PathVariable(value = "id") String id, @RequestBody Ship ship) {
        Long idl;
        try {
            idl = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            throw new BadRequestException();
        }
        return service.updateShip(idl, ship);
    }

    @GetMapping(value = "/ships")
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> getAllShips(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "planet", required = false) String planet,
                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                  @RequestParam(value = "after", required = false) Long after,
                                  @RequestParam(value = "before", required = false) Long before,
                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                  @RequestParam(value = "maxRating", required = false) Double maxRating,
                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        return  service.pagination(service.getAllShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating, order), pageNumber, pageSize);
    }

    @RequestMapping(value = "/ships/count", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "planet", required = false) String planet,
                            @RequestParam(value = "shipType", required = false) ShipType shipType,
                            @RequestParam(value = "after", required = false) Long after,
                            @RequestParam(value = "before", required = false) Long before,
                            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                            @RequestParam(value = "minRating", required = false) Double minRating,
                            @RequestParam(value = "maxRating", required = false) Double maxRating,
                            @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order){

        return service.getShipsCount(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating, order);
    }

}
