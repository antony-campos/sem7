/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semana7.venta.model;

import jakarta.persistence.*;


/**
 *
 * @author LAB_P03
 */
@Entity
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String producto;
    private int cantidad;

    @Column(name = "precio_total")
    private double precio_total;          

    @Column(name = "fecha_venta")
    private String fecha_venta;

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

}
