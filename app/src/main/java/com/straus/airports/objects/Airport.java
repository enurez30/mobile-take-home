package com.straus.airports.objects;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Airport {
    @Id
    private Long id;

    private String name;
    private String city;
    private String country;
    private String IATA;
    private Double Latitude;
    private Double Longitude;
    @Generated(hash = 197447753)
    public Airport(Long id, String name, String city, String country, String IATA,
            Double Latitude, Double Longitude) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.IATA = IATA;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }
    @Generated(hash = 648016182)
    public Airport() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getIATA() {
        return this.IATA;
    }
    public void setIATA(String IATA) {
        this.IATA = IATA;
    }
    public Double getLatitude() {
        return this.Latitude;
    }
    public void setLatitude(Double Latitude) {
        this.Latitude = Latitude;
    }
    public Double getLongitude() {
        return this.Longitude;
    }
    public void setLongitude(Double Longitude) {
        this.Longitude = Longitude;
    }


}
