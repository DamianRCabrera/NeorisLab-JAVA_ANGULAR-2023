package com.neoris.turnosrotativos.repository;

import com.neoris.turnosrotativos.entities.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Definimos un Repository para los Conceptos con la annotation "@Repository"
//Extiende de JPARepository y le pasamos dentro la entidad que contendrá dentro
//Y el tipo de dato del Id o primary key que usará.
@Repository
public interface RepositoryConcepto extends JpaRepository<Concepto, Integer> {
}
