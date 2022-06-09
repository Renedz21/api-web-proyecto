package com.grupo6.proyecto.grupo6.controller;

import com.grupo6.proyecto.grupo6.entity.Data;
import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.pojo.CantidadGenero;
import com.grupo6.proyecto.grupo6.service.IRegistrationsService;
import com.grupo6.proyecto.grupo6.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class IDashboardRestController {

    @Autowired
    private IUserService usuarioService;
    @Autowired
    private IRegistrationsService registrationsService;


    @GetMapping("/dashboard")
    public ResponseEntity<?> show() {
        Map<String, Object> response = new HashMap<>();
        Integer cantUser,cantRegistrations,cantmas,cantfem;
        List<CantidadGenero> cantGenero = new ArrayList<>();
        try {
            cantUser = usuarioService.cantidadUser();
            cantRegistrations = registrationsService.catRegistrations();
            cantmas = usuarioService.countByGenero("Masculino");
            cantfem = usuarioService.countByGenero("Femenino");
            cantGenero.get(0).setFemenino(String.valueOf(cantfem));
            cantGenero.get(0).setMasculino(String.valueOf(cantmas));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar la Data en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("cantUser", cantUser);
        response.put("cantRegistrations", cantRegistrations);
        response.put("resultFiltroDesdeHasta", "");
        response.put("cantPorGenero", cantGenero);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }



}
