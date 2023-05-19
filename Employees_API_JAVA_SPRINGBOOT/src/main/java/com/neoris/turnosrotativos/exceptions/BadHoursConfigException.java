package com.neoris.turnosrotativos.exceptions;

public class BadHoursConfigException extends RuntimeException{

    //Exception creada a partir de una RuntimeException que recibe un mensaje en su constructor
    //Esta exception se lanzará cuando el parámetro de las horas trabajadas en el request
    //Tenga errores de acuerdo a la User Story
    public BadHoursConfigException(String message){
        super(message);
    }
}
