package com.straus.airports.objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Airline {
    @Id
    private Long id;

    private String name;
    private String twoDigitZIPcode;
    private String threeDigitZIPcode;
    private String country;
    @Generated(hash = 2083151413)
    public Airline(Long id, String name, String twoDigitZIPcode,
            String threeDigitZIPcode, String country) {
        this.id = id;
        this.name = name;
        this.twoDigitZIPcode = twoDigitZIPcode;
        this.threeDigitZIPcode = threeDigitZIPcode;
        this.country = country;
    }
    @Generated(hash = 1973799177)
    public Airline() {
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
    public String getTwoDigitZIPcode() {
        return this.twoDigitZIPcode;
    }
    public void setTwoDigitZIPcode(String twoDigitZIPcode) {
        this.twoDigitZIPcode = twoDigitZIPcode;
    }
    public String getThreeDigitZIPcode() {
        return this.threeDigitZIPcode;
    }
    public void setThreeDigitZIPcode(String threeDigitZIPcode) {
        this.threeDigitZIPcode = threeDigitZIPcode;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    
}
