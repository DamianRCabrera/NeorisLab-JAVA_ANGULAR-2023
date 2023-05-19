package com.neoris.turnosrotativos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

//Generamos una Entity y Table llamada Jornada
//Con Constructor de todos los Argumentos y también
//Constructor sin ningún Argumento.

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jornada {

    //Se genera un @Id automáticamente cada vez que se instancia y este
    //Será el Id de cada una de las jornadas instanciadas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Se genera un field Integer nroDocumento con su respectiva Column
    //Como dicho valor saldrá de un empleado correctamente guardado
    //Decidí no agregar más que la validación de que no sea un valor NULL
    //Ya que de eso debería ocuparse la entidad Empleado.
    @Column(nullable = false)
    @NotNull(message = "El campo nroDocumento es obligatorio")
    private Integer nroDocumento;

    //Se genera un field String nombreCompleto con su respectiva Column
    //Como dicho valor saldrá de un empleado correctamente guardado
    //Decidí no agregar más que la validación de que no sea un valor NULL
    //Ya que de eso debería ocuparse la entidad Empleado.
    @Column(nullable = false)
    @NotNull(message = "El campo nombreCompleto es obligatorio")
    private String nombreCompleto;

    //Se genera un field String concepto con su respectiva Column
    //Como dicho valor saldrá de un concepto correctamente guardado
    //Decidí no agregar más que la validación de que no sea un valor NULL
    //Ya que de eso debería ocuparse la entidad Concepto.
    @Column(nullable = false)
    @NotNull(message = "El campo concepto es obligatorio")
    private String concepto;

    //Se genera un field LocalDate fecha con su respectiva Column
    //En esta caso debemos validar que el valor no se NULL y además
    //Le seteamos el formato en "yyyy-MM-dd"
    //En este caso no usamos @Past o annotations similares, ya que,
    //Entiendo que podemos cargar fechas pasadas, presentes y futuras según
    //Lo solicitado en la consigna del ejercicio.
    @Column(nullable = false)
    @NotNull(message = "El campo fecha es obligatorio")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fecha;

    //Se genera un field Integer hsTrabajadas con su respectiva Column
    //En este caso decidí no agregar annotations de validation porque
    //Dichas validaciones estarán comtempladas en el Service.
    @Column
    private Integer horasTrabajadas;
}
