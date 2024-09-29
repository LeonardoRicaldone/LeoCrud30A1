/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Leonardo
 */
public class Cocinero {

    private int idCocinero;
    private String nombreCocinero;
    private int edadCocinero;
    private double pesoCocinero;
    private String correoCocinero;

    // Constructor
    public Cocinero(int idCocinero, String nombreCocinero, int edadCocinero, double pesoCocinero, String correoCocinero) {
        this.idCocinero = idCocinero;
        this.nombreCocinero = nombreCocinero;
        this.edadCocinero = edadCocinero;
        this.pesoCocinero = pesoCocinero;
        this.correoCocinero = correoCocinero;
    }

    // Getters y setters
    public int getIdCocinero() {
        return idCocinero;
    }

    public void setIdCocinero(int idCocinero) {
        this.idCocinero = idCocinero;
    }

    public String getNombreCocinero() {
        return nombreCocinero;
    }

    public void setNombreCocinero(String nombreCocinero) {
        this.nombreCocinero = nombreCocinero;
    }

    public int getEdadCocinero() {
        return edadCocinero;
    }

    public void setEdadCocinero(int edadCocinero) {
        this.edadCocinero = edadCocinero;
    }

    public double getPesoCocinero() {
        return pesoCocinero;
    }

    public void setPesoCocinero(double pesoCocinero) {
        this.pesoCocinero = pesoCocinero;
    }

    public String getCorreoCocinero() {
        return correoCocinero;
    }

    public void setCorreoCocinero(String correoCocinero) {
        this.correoCocinero = correoCocinero;
    }
}
