/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.semana7.venta.repository;

import com.semana7.venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LAB_P03
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{
    
}
