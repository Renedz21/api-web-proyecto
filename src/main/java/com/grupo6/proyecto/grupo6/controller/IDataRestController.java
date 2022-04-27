package com.grupo6.proyecto.grupo6.controller;

import com.grupo6.proyecto.grupo6.entity.Data;
import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.service.IDataService;
import com.grupo6.proyecto.grupo6.service.IRecomendationsService;
import com.grupo6.proyecto.grupo6.service.IRegistrationsService;
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
public class IDataRestController {

    @Autowired
    private IDataService dataService;

    @Autowired
    private IRecomendationsService recomendationsService;

    @GetMapping("/data")
    public List<Data> index() {
        return dataService.findAll();
    }

    @GetMapping("/data/page")
    public Page<Data> index(@RequestParam Integer pageNumber, @RequestParam Integer perPage) {
        Pageable pageable = PageRequest.of(pageNumber, perPage);
        Page<Data> user = dataService.findAll(pageable);
        return user;
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Data data = null;
        Map<String, Object> response = new HashMap<>();
        try {
            data = dataService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar la Data en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (data == null) {
            response.put("mensaje", "La Data con ID:  ".concat(id.toString().concat(" no existe en al base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("data", data);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/data/result/{id}")
    public ResponseEntity<?> show(@PathVariable String id) {
        List<Data> data = null;
        Recomendations recomendations = null;
        Map<String, Object> response = new HashMap<>();
        Integer prom =0;
        Integer datCal = 0;
        try {

            String idparam = null;
            if(id != null) idparam = "%"+id+"%";
            data = dataService.resultData(idparam);
            Long numLevel;
            for(int a =0; a < data.toArray().length; a++){
                prom += Integer.parseInt(String.valueOf(data.get(a).getDataLevel()));
            }
            datCal = prom/data.toArray().length;
            recomendations = recomendationsService.findAllByLevel(Long.parseLong(String.valueOf(Math.round(datCal))));

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar la Data en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (data == null) {
            response.put("mensaje", "La Data con ID:  ".concat(id.toString().concat(" no existe en al base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("promedio", datCal);
        response.put("type", "success");
        response.put("recomendations", recomendations);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/data")
    public ResponseEntity<?> create(@Valid @RequestBody Data data,
                                    BindingResult result) {
        Data datanew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            datanew = dataService.save(data);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear la Data");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La data ha sido creado con Exito");
        response.put("type","success");
        response.put("data", datanew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    @PutMapping("/data/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Data data,
                                    BindingResult result,
                                    @PathVariable Long id) {
        Data dataActual = dataService.findById(id);
        Map<String, Object> response = new HashMap<>();
        Data dataUpdate = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (dataActual == null) {
            response.put("mensaje", "Error el cliente ID:  ".concat(id.toString().concat(" no se pudo editar")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            dataActual.setName(data.getName());
            dataActual.setDataLevel(data.getDataLevel());
            dataUpdate = dataService.save(dataActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la Data");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La data ha sido atualizado con Exito");
        response.put("type","success");
        response.put("data", dataUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    @DeleteMapping("/data/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            dataService.delete(id);
        } catch (DataAccessException e) {
            response.put("type","error");
            response.put("mensaje","Error en eliminar el usuario , comunicarse con Frank");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Data eliminado con Exito");
        response.put("type","success");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }


}
