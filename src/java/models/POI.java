/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author viroc
 */
public class POI {

    int id;
    String contributor, title, type, lon, lat, desc;
    Date TOC;

    public int getId() {
        return id;
    }

    public String getContributor() {
        return contributor;
    }

    public String getTitle() {
        return title;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getDesc() {
        return desc;
    }
    
    public String getType() {
        return type;
    }

    public Date getTOC() {
        return TOC;
    }

    public POI(int id, String contributor, String title, String type, String lon, String lat, String desc, Date TOC) {
        this.id = id;
        this.contributor = contributor;
        this.title = title;
        this.lon = lon;
        this.lat = lat;
        this.desc = desc;
        this.TOC = TOC;
        this.type = type;
    }
}
