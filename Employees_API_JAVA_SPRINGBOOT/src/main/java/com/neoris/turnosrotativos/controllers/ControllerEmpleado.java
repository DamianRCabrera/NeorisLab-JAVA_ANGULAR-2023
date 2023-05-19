package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.services.impl.ServiceEmpleadoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//Se define una @RestController esta es una annotation que combina
//Las annotations @Controller y @ResponseBody para facilitar la creación
//Del controlador. Este gestionará las peticiones HTTP y devolverá los datos en formato JSON.
@RestController
//Seteamos el mappeo de la request con "/empleado" que es el Endpoint donde se harán
//las Requests.
@RequestMapping("/empleado")
public class ControllerEmpleado {

    //Conectamos el Controller con el Service implementado
    //De Empleado.
    @Autowired
    ServiceEmpleadoImpl serviceEmpleadoImpl;

    //Generamos un @PostMapping para las request "POST" seteando lo que pasaremos como argumento
    //Para que sea validado con @Valid y tambien usamos @RequestBody para "bindear" el request body
    //de la llamda Http Request a la Entidad Empleado.
    //Acá estaremos retornando lo que retorna el llamado al método "saveEmpleado()" de la implementación
    //Del Service y además en la ResponseEntity estaremos definiendo el status code que en este caso
    //Será 201-Created.
    @PostMapping
    public ResponseEntity saveEmpleado(@Valid @RequestBody Empleado employee){
        return new ResponseEntity(serviceEmpleadoImpl.saveEmpleado(employee), HttpStatus.CREATED);
    }


    //Generamos un @GetMapping para las request "GET" que lleguen a la Endpoint definida.
    //Acá estaremos retornando el resultado de invocar el método de "getEmpleadoList()"
    //Del Service de Empleado y además estaremos seteando el status 200 en la respuesta.
    @GetMapping
    public ResponseEntity getAllEmpleados(){
        return new ResponseEntity(serviceEmpleadoImpl.getEmpleadoList(), HttpStatus.OK);
    }

    //Generamos un @GetMapping para las request de "GET" que lleguen al Endpoint definido
    // (al cual se le agregado ("/{Id}"), adicional, para extraer la información del path
    //usamos la annotation @PathVariable que extraerá esa información para pasarla
    //Al método en forma de un Integer id. Dicho "id" se le pasará como argumento a la
    //Invocación del método "getEmpleadoById()" del Service implementado de Empleado.
    //En este caso estaremos retornando la respuesta con el Empleado y el status code
    //seteado 200.
    @GetMapping("/{id}")
    public ResponseEntity getEmpleadoById(@PathVariable Integer id){
        Empleado employee = serviceEmpleadoImpl.getEmpleadoById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    //Generamos un @DeleteMapping para las request "DELETE" que lleguen al Endpoint definido
    //para extraer la variable del Path "{/id}" usamos nuevamente la annotation @Pathvariable
    //designando un Integer id que contendrá el valor.
    //Para este caso implementé un try/catch porque el metodo "deleteEmpleadoById(id)" del
    //Service implementado de Empleado puede arrojar una excepción del tipo "ResourceNotFound"
    //Que se detallará en el archivo pertinente. En el catch, "agarramos" esa excepción" y
    //Seteamos un map con la respuesta a entregar y el status 404.
    //En caso de que lo encuentre, retornaremos la respuesta simplemente con el status NO CONTENT
    //Que no posee body.
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmpleadoById(@PathVariable Integer id){
        Map<String, String> resultMap = new HashMap<>();

        try {
            serviceEmpleadoImpl.deleteEmpleadoById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException exception){
            resultMap.put("error", "No se encontró el empleado con Id: " + id);
            return new ResponseEntity(resultMap, HttpStatus.NOT_FOUND);
        }
    }

    //Generamos un @PutMapping para las request de tipo "PUT" que lleguen al Endpoint definido
    //En este caso obtenemos la path variable "id" nuevamente con @PatchVariable, a su vez, validamos
    //el request body y lo bindeamos al objeto Empleado.
    //Estaremos retornando el resultado de la invocación del método "updateEmpleadoById()" del Service
    //Implementado para Empleado. En caso de haber excepciones por no ser válido el body de la request
    //Lo estará handleando nuestro exceptionHandler que será profundizado en el archivo pertinente.
    //A su vez setearemos el statuscode como 200
    @PutMapping("/{id}")
    public ResponseEntity updateEmpleadoById(@PathVariable Integer id, @Valid @RequestBody Empleado updatedEmployee){
        return new ResponseEntity(serviceEmpleadoImpl.updateEmpleadoById(id, updatedEmployee), HttpStatus.OK);
    }
}
