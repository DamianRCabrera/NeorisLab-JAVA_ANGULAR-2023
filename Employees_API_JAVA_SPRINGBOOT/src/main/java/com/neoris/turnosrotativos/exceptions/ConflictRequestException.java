package com.neoris.turnosrotativos.exceptions;

public class ConflictRequestException extends RuntimeException{

    //Exception creada a partir de una RuntimeException que recibe un mensaje en su constructor
    //Esta exception se lanzará cuando el parámetro en el request
    //Tenga errores de acuerdo a la User Story y deba enviar un 409.
    public ConflictRequestException(String message){
        super(message);
    }
}
