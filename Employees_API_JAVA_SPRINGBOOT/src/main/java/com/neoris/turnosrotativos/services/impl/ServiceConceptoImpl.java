package com.neoris.turnosrotativos.services.impl;

import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.repository.RepositoryConcepto;
import com.neoris.turnosrotativos.services.ServiceConcepto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Se define que esta clase es un Service con la annotation @Service
//Implementa la interface ServiceConcepto

@Service
public class ServiceConceptoImpl implements ServiceConcepto {

    //Se conecta con el Repository de Concepto para tener los métodos disponibles que lidian con la DB
    @Autowired
    RepositoryConcepto repositoryConcepto;

    //Implementación del método que trae una Lista con todas las Jornadas guardadas en la DB
    @Override
    public List<Concepto> getConceptoList(){
        return repositoryConcepto.findAll();
    }
}
