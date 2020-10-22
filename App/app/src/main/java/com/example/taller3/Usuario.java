package com.example.taller3;

import android.net.CaptivePortal;

public class Usuario {

    private String name;
    private String apellido;
    private int id;
    private double latitude;
    private double longitude;
    private boolean disponible;

    public Usuario(){

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getApellido(){
        return apellido;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public boolean getDisponible() { return disponible; }
}
