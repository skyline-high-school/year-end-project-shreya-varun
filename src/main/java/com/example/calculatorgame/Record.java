package com.example.calculatorgame;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Record implements Comparable<Record> {
    public String username;
    public double time;
    @JsonCreator
    public Record(@JsonProperty("username") String username, @JsonProperty("time") double time) {
        this.username = username;
        this.time = time;
    }
    @Override
    public int compareTo(Record o) { return Double.compare(this.time, o.time); }
}
