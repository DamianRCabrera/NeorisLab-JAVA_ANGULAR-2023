package com.neoris.turnosrotativos.controllers;


import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.services.impl.ServiceJornadaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

//Se define una @RestController esta es una annotation que combina
//Las annotations @Controller y @ResponseBody para facilitar la creación
//Del controlador. Este gestionará las peticiones HTTP y devolverá los datos en formato JSON.
@RestController
//Seteamos el mappeo de la request con "/jornada" que es el Endpoint donde se harán
//las Requests.
@RequestMapping("/jornada")
public class ControllerJornada {

    //Conectamos el Controller con el Service implementado
    //De Jornada.
    @Autowired
    ServiceJornadaImpl serviceJornadaImpl;

    //Generamos un @PostMapping
    //Validando los datos y bindeando el RequestBody con el objeto JornadaDTO
    //A su mismo pasamos el objeto al metodo "saveJornada" y lo que retorne el método será el body
    //De la respuesta, seteando también el status 201.
    @PostMapping
    public ResponseEntity saveJornada(@Valid @RequestBody JornadaDTO jornadaDTO){
        return new ResponseEntity(serviceJornadaImpl.saveJornada(jornadaDTO), HttpStatus.CREATED);
    }

    //Generamos un "GetMapping"
    //Obtenemos los params pasados en el llamado a la API y los pasamos a variables
    //Integer para nroDocumento, sin ser "requerido"
    //LocalDate para fecha, sin ser "requerido" y dandole el formato que necesitamos

    //Dentro del Controller usamos if's para validar si alguno o ambos params son nulos, manejando que enviar en cada caso.
    //Cada uno de los if's tiene su propio método en el Service que retorno lo correspondiente en cada caso
    //Se ampliará sobre este punto en el archivo pertinente.
    @GetMapping
    public ResponseEntity getAllJornadas(@RequestParam(required = false) Integer nroDocumento, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha){
        if(nroDocumento != null && fecha != null){
            return new ResponseEntity(serviceJornadaImpl.getAllByNroDocumentoAndFecha(nroDocumento, fecha), HttpStatus.OK);
        } else if(nroDocumento != null){
            return new ResponseEntity(serviceJornadaImpl.getAllByNroDocumento(nroDocumento), HttpStatus.OK);
        } else if(fecha != null){
            return new ResponseEntity(serviceJornadaImpl.getAllByFecha(fecha), HttpStatus.OK);
        } else {
            return new ResponseEntity(serviceJornadaImpl.getAllJornadas(), HttpStatus.OK);
        }
    }
}
