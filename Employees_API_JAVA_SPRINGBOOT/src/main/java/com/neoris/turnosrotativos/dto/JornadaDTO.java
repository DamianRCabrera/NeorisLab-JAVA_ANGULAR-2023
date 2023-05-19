package com.neoris.turnosrotativos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


//Se genera un DATA TRANSFER OBJECT (DTO) para recibir
//Los datos de la request y manejar dichos datos para
//Obtener la información necesaria que deberá ser persistida
//En la Entidad Tabla Jornada dentro de su respectivo repository.
//Nuevamente se generan constructores con y sin argumentos.
//Se define que será un Componente de la App SpringBoot
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JornadaDTO {

    //Se define un field Integer idEmpleado que no puede ser NULL
    //Y que contendrá el dato del idEmpleado que deberemos buscar
    //En su debido momento.
    @NotNull(message = "idEmpleado es obligatorio.")
    private Integer idEmpleado;

    //Se define un field Integer idConcepto que no puede ser NULL
    //Y que contendrá el dato del idConcepto que deberemos buscar
    //En su debido momento.
    @NotNull(message = "idConcepto es obligatorio.")
    private Integer idConcepto;

    //Se define un field LocalDate con su respectivo formato.
    //Dicho LocalDate no puede estar NULL
    @NotNull(message = "Fecha es obligatorio")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fecha;

    //Se define un field Integer horasTrabajadas
    //No posee validaciones ya que este field puede ser NULL o tener un valor
    //Dependiendo del concepto que se pase como otro argumento.
    //Dichas validaciones se implementarán en el Service.
    private Integer horasTrabajadas;
}
