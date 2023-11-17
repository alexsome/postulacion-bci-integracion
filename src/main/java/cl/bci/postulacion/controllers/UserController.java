package cl.bci.postulacion.controllers;

import cl.bci.postulacion.models.User;
import cl.bci.postulacion.services.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(value = "Administracion Usuario")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "to get all users", response = User.class)
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "to get all users", response = User.class)
    public ResponseEntity<?> userByEmail(@PathVariable String email){
        User user = this.userService.findUserByEmail(email);
        if(Objects.isNull(user)) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Error al buscar el usuario");
            response.put("error","El correo no esta registrado");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user){
        Map<String, Object> response = new HashMap<>();
        try {
            this.userService.createUser(user);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al crear el usuario");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
