package com.grupo6.proyecto.grupo6.controller;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.entity.User;
import com.grupo6.proyecto.grupo6.service.IRecomendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class IRecomendationsRestController {

    @Autowired
    private IRecomendationsService recomendationsService;

    @GetMapping("/recomendation")
    public List<Recomendations> index() {
        return recomendationsService.findAll();
    }

    @GetMapping("/recomendation/page")
    public Page<Recomendations> index(@RequestParam Integer pageNumber, @RequestParam Integer perPage) {
        Pageable pageable = PageRequest.of(pageNumber, perPage);
        Page<Recomendations> rec = recomendationsService.findAll(pageable);
        return rec;
    }

    @GetMapping("/recomendation/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Recomendations recomendations = null;
        Map<String, Object> response = new HashMap<>();
        try {
            recomendations = recomendationsService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar la recomendacion en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (recomendations == null) {
            response.put("mensaje", "La Recomendacion con ID:  ".concat(id.toString().concat(" no existe en al base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("recomendations", recomendations);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/recomendation")
    public ResponseEntity<?> create(@Valid @RequestBody Recomendations recomendations,
                                    BindingResult result) {
        Recomendations recnew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            recnew = recomendationsService.save(recomendations);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear la Recomendacion");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La Recomendacion ha sido creado con Exito");
        response.put("type","success");
        response.put("recomendation", recnew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @PutMapping("/recomendation/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Recomendations recomendations,
                                    BindingResult result,
                                    @PathVariable Long id) {
        Recomendations recActual = recomendationsService.findById(id);
        Map<String, Object> response = new HashMap<>();
        Recomendations recUpdate = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (recActual == null) {
            response.put("mensaje", "Error el Usuario ID:  ".concat(id.toString().concat(" no se pudo editar")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            recActual.setName(recomendations.getName());
            recActual.setLevel(recomendations.getLevel());

            recUpdate = recomendationsService.save(recActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la recomendacion");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Recomendacion ha sido atualizado con Exito");
        response.put("type","success");
        response.put("recomendation", recUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/recomendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            recomendationsService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje","Error en eliminar la recomendacions , comunicarse con Frank");
            response.put("type","error");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Recomendacion eliminado con Exito");
        response.put("type","success");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }


}
