package com.neoris.turnosrotativos.repository;

import com.neoris.turnosrotativos.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Definimos un Repository para los Empleados con la annotation "@Repository"
//Extiende de JPARepository y le pasamos dentro la entidad que contendrá dentro
//Y el tipo de dato del Id o primary key que usará.

//Tambien dentro implementará dos métodos que Springboot generará para checkear si
//Existe ese empleado con ese nroDocumentoo con ese email
@Repository
public interface RepositoryEmpleado extends JpaRepository<Empleado, Integer> {

    boolean existsByNroDocumento(Integer nroDocumento);

    boolean existsByEmail(String email);
}
