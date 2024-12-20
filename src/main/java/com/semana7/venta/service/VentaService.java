/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.semana7.venta.service;

import com.semana7.venta.model.Venta;
import com.semana7.venta.repository.VentaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LAB_P03
 */
@Service
public class VentaService {
    @Autowired
    private VentaRepository repository;

    public List<Venta> listarTodas() {
        return repository.findAll();
    }

    public void guardar(Venta venta) {
        repository.save(venta);
    }

    public Optional<Venta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}

