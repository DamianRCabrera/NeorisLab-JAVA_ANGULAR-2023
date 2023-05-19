package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.services.impl.ServiceConceptoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Se define una @RestController esta es una annotation que combina
//Las annotations @Controller y @ResponseBody para facilitar la creación
//Del controlador. Este gestionará las peticiones HTTP y devolverá los datos en formato JSON.

@RestController

//Seteamos el mappeo de la request con "/concepto" que es el Endpoint donde se harán
//las Requests.
@RequestMapping("/concepto")
public class ControllerConcepto {

    //Conectamos el Controller con la clase Implementada del Service para Concepto.
    @Autowired
    ServiceConceptoImpl serviceConceptoImpl;

    //Generamos un GetMapping para manejar las Requests "GET" que lleguen al Endpoint definido
    //Retornamos el resultado de invocar al método "getConceptoList()" del servicio
    //Y seteamos el status de la respuesta con code 200.
    @GetMapping
    public ResponseEntity getAllConcepto(){
        return new ResponseEntity(serviceConceptoImpl.getConceptoList(), HttpStatus.OK);
    }
}
