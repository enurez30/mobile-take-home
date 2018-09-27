package com.straus.airports.objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Route {
    @Id
    private Long id;

    private String airlineId;
    private String origin;
    private String destination;
    @Generated(hash = 2064074508)
    public Route(Long id, String airlineId, String origin, String destination) {
        this.id = id;
        this.airlineId = airlineId;
        this.origin = origin;
        this.destination = destination;
    }
    @Generated(hash = 467763370)
    public Route() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAirlineId() {
        return this.airlineId;
    }
    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }
    public String getOrigin() {
        return this.origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return this.destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    
}
