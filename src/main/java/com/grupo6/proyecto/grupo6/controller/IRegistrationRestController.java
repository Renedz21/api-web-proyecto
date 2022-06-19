package com.grupo6.proyecto.grupo6.controller;

import com.grupo6.proyecto.grupo6.entity.Recomendations;
import com.grupo6.proyecto.grupo6.entity.Registrations;
import com.grupo6.proyecto.grupo6.entity.User;
import com.grupo6.proyecto.grupo6.pojo.CantRepWord;
import com.grupo6.proyecto.grupo6.pojo.RegistrationData;
import com.grupo6.proyecto.grupo6.service.IRecomendationsService;
import com.grupo6.proyecto.grupo6.service.IRegistrationsService;
import com.grupo6.proyecto.grupo6.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Registration;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class IRegistrationRestController {

    @Autowired
    private IRegistrationsService registrationsService;

    @Autowired
    private IUserService userService;

    //funciona
    @GetMapping("/registrations")
    public List<Registrations> index() {
        return registrationsService.findAll();
    }

    //funciona
    @GetMapping("/registrations/page")
    public Page<Registrations> index(@RequestParam Integer pageNumber, @RequestParam Integer perPage) {
        Pageable pageable = PageRequest.of(pageNumber, perPage);
        Page<Registrations> reg = registrationsService.findAll(pageable);
        return reg;
    }

    //funciona
    @GetMapping("/registrations/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Registrations registrations = null;
        Map<String, Object> response = new HashMap<>();
        try {
            registrations = registrationsService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al buscar el Registro en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (registrations == null) {
            response.put("mensaje", "El Registro con ID:  ".concat(id.toString().concat(" no existe en al base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("registrations", registrations);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //funciona
    @PostMapping("/registrations")
    public ResponseEntity<?> create(@RequestBody Registrations data ) {
        //-----------------INICIO SETEO DE DATOS-----------------
        Registrations registrations = new Registrations();
        //-----------------FIN SETEO DE DATOS-----------------
        Registrations regnew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            regnew = registrationsService.save(data);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el Registro");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El Registro ha sido creado con Exito");
        response.put("type","success");
        response.put("registrations", regnew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    //crear usuario desde el formulario
    @PostMapping("/users/form")
    public ResponseEntity<?> create(@Valid @RequestBody User cliente,
                                    BindingResult result) {
        User usernew = null;
        String type ="registration";
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El Campo '" + err.getField() + "'" + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            cliente.setType(type);
            usernew = userService.save(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el User");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con Exito");
        response.put("type","success");
        response.put("user", usernew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    //funciona
    @PutMapping("/registrations/{id}")
    public ResponseEntity<?> update(@RequestBody Registrations registrations,
                                    @PathVariable Long id) {
        Registrations regActual = registrationsService.findById(id);
        Map<String, Object> response = new HashMap<>();
        Registrations regUpdate = null;

        if (regActual == null) {
            response.put("mensaje", "Error el Usuario ID:  ".concat(id.toString().concat(" no se pudo editar")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            regActual.setRecomendationId(registrations.getRecomendationId());
            regActual.setWord(registrations.getWord());

            regUpdate = registrationsService.save(regActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el registrations");
            response.put("type","error");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El registro ha sido atualizado con Exito");
        response.put("type","success");
        response.put("registrations", regUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    //funciona
    @DeleteMapping("/registrations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            registrationsService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje","Error en eliminar el registro , comunicarse con Frank");
            response.put("type","error");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Registro eliminado con Exito");
        response.put("type","success");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }

    @GetMapping("/registrations/cantidad")
    public List<CantRepWord> returnCant() {
        List<CantRepWord> cant = registrationsService.cantRep();
        return cant;
    }


}
