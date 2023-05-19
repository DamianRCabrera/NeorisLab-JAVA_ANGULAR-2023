package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.dto.JornadaDTO;

import java.time.LocalDate;
import java.util.List;

//Interface para definir métodos de lo que será el Service para Jornadas

public interface ServiceJornada {
    Jornada saveJornada(JornadaDTO jornadaRequest);

    List<Jornada> getAllJornadas();

    List<Jornada> getAllByNroDocumento(Integer nroDocumento);

    List<Jornada> getAllByFecha(LocalDate fecha);

    List<Jornada> getAllByNroDocumentoAndFecha(Integer nroDocumento, LocalDate fecha);

}
