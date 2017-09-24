package com.codepath.nytimessearch.models;

import java.util.Date;

/**
 * Created by chenrangong on 9/24/17.
 */

public class Filter {

    public Date beginDate;
    public String sortOrder;
    public boolean arts;
    public boolean fashion;
    public boolean sports;

    public Filter(){
        this.beginDate = new Date(0);
        this.sortOrder = "newest";
    }
}
