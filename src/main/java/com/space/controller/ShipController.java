package com.space.controller;

import com.space.Exeptions.BadRequestException;
import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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
}
