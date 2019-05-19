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
        try {
            Long idl = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            throw new BadRequestException();
        }
        return service.getShip(Long.parseLong(id));
    }
}
