package com.example.lab7_20190271.controller;

import com.example.lab7_20190271.entity.Users;
import com.example.lab7_20190271.repository.UsersRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {
    final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository){
        this.usersRepository=usersRepository;
    }
    //listar
    @GetMapping(value = {"/list", ""})
    public List<Users> listarUsuarios(){
        return usersRepository.findAll();
    }
    //obtener
    @GetMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarUsers(@PathVariable("Id") String idStr) {


        try {
            int id = Integer.parseInt(idStr);
            Optional<Users> byId = usersRepository.findById(id);

            HashMap<String, Object> respuesta = new HashMap<>();

            if (byId.isPresent()) {
                respuesta.put("result", "ok");
                respuesta.put("users", byId.get());
            } else {
                respuesta.put("result", "no existe");
            }
            return ResponseEntity.ok(respuesta);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //actualizar
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Users userRecibido ) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (userRecibido.getId() != null && userRecibido.getId() > 0) {

            Optional<Users> byId = usersRepository.findById(userRecibido.getId());
            if (byId.isPresent()) {
                Users usersFromDb = byId.get();

                if (userRecibido.getId() != null)
                    usersFromDb.setName(userRecibido.getName());

                if (userRecibido.getType() != null)
                    usersFromDb.setType(userRecibido.getType());

                if (userRecibido.getResources() != null)
                    usersFromDb.setResources(userRecibido.getResources());

                if (userRecibido.getActive() != null)
                    usersFromDb.setActive(userRecibido.getActive());

                usersRepository.save(usersFromDb);
                rpta.put("result", "ok");
                return ResponseEntity.ok(rpta);
            } else {
                rpta.put("result", "error");
                rpta.put("msg", "El ID del usuario enviado no existe");
                return ResponseEntity.badRequest().body(rpta);
            }
        } else {
            rpta.put("result", "error");
            rpta.put("msg", "debe enviar un usuario con ID");
            return ResponseEntity.badRequest().body(rpta);
        }
    }
}
