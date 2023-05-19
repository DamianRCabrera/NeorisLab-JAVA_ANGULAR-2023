package com.neoris.turnosrotativos.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    //Exception creada a partir de una RuntimeException que recibe un mensaje en su constructor
    //Esta exception se lanzará cuando se busque algún recurso dentro de los repositories y no sea encontrado
    //Debe enviar un 404.
    public ResourceNotFoundException(String message){
        super(message);
    }
}
