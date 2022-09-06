/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backend.server.repository;

import com.backend.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author M4N0
 */
public interface ServerRepository extends JpaRepository<Server,Long>{
    Server findByIpAddress(String ipAddress);
    //Server findByName(String name);    este metodo no serviria porque si hay mas de un objeto con el mismo nombre se genera una excepcion
}
