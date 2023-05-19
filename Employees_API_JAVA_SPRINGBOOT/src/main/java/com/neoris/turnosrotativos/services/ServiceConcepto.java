package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.entities.Concepto;

import java.util.List;

//Interface para definir métodos de lo que será el Service para Conceptos

public interface ServiceConcepto {
    List<Concepto> getConceptoList();
}
