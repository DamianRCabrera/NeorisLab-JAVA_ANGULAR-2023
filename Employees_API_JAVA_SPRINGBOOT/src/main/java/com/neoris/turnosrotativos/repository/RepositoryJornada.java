package com.neoris.turnosrotativos.repository;

import com.neoris.turnosrotativos.entities.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//Definimos un Repository para las Jornadas con la annotation "@Repository"
//Extiende de JPARepository y le pasamos dentro la entidad que contendrá dentro
//Y el tipo de dato del Id o primary key que usará.

//Tambien dentro implementará varios métodos que Springboot generará:
//Dentro de ellos podemos notar que son:
//1) Si existe esa jornada por NroDocumento, nombre de Concepto y por Fecha
//2) Si existe por NroDocumento y Fecha
//3) Generar una lista de todos las jornadas que encuentre por NroDocumento
//4) Lo mismo para Fecha
//5) Para ambos casos, o sea, por Fecha y por NroDocumento al mismo tiempo.
//6) Definimos un método que va a usar una query donde retornará las Jornadas que tengan el mismo
//nroDocumento que se le pasa al método, y una fecha entre el "lunes" de esa fecha y el "domingo" de esa fecha
//según requirimiento de la user story
//7) Por último un método count donde retornaremos un Integer con las coincidencias que encuentre que coincidan
//con el nroDocumento, nombre de Concepto pasado y las fechas entre medio explicado en el punto anterior.

@Repository
public interface RepositoryJornada extends JpaRepository<Jornada, Integer> {
    boolean existsByNroDocumentoAndConceptoAndFecha(Integer nroDocumento, String concepto, LocalDate fecha);

    boolean existsByNroDocumentoAndFecha(Integer nroDocumento, LocalDate fecha);

    List<Jornada> findAllByNroDocumento(Integer nroDocumento);

    List<Jornada> findAllByFecha(LocalDate fecha);

    List<Jornada> findAllByNroDocumentoAndFecha(Integer nroDocumento, LocalDate fecha);

    @Query("SELECT jor FROM Jornada jor WHERE jor.nroDocumento = :nroDocumento AND jor.fecha BETWEEN :startDate AND :endDate")
    List<Jornada> findAllCreatedInWeekForNroDocumento(@Param("nroDocumento") Integer nroDocumento, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Integer countAllByNroDocumentoAndConceptoAndFechaBetween(Integer nroDocumento, String concepto, LocalDate startDate, LocalDate endDate);
}
