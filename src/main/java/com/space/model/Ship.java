package com.space.model;


import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Validated
@Table(name = "ship")
public class Ship {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", length=50)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "planet", length=50)
    private String planet;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "shipType")
    private ShipType shipType;

    @NotNull
    @FutureOrPresent
    @Column(name = "prodDate")
    private Date prodDate;

    @Column(name = "isUsed")
    private Boolean isUsed;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("0.99")
    @Column(name = "speed")
    private Double speed;

    @NotNull
    @Min(1)
    @Max(9999)
    @Column(name = "crewSize")
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}