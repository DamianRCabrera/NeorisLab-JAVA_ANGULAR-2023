package com.neoris.turnosrotativos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.neoris.turnosrotativos.validations.annotations.Adult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

//Creamos una entidad llamada Empleados, la definimos como @Table y generamos
//constructores con y sin argumentos

@Entity(name="empleados")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    //Generamos un field @Id que se auto-genera cada vez que se instancia un Empleado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Generamos un field Integer nroDocumento definimos que tendrá su
    //Column que no podrá contener datos NULL
    //Además agregué dos validaciones de "min" y "max" para que no se ingresen valores
    //excesivamente bajos o excesivamente altos.
    //Además con el @NotNull validamos que llegue un valor y que el dato no sea NULL.
    @Column(nullable = false)
    @Min(value = 1000000, message = "Nro documento no puede ser menor a 1.000.000")
    @Max(value = 999999999, message = "Nro documento no puede ser mayor a 999.999.999")
    @NotNull(message = "El campo nroDocumento es obligatorio")
    private Integer nroDocumento;

    //Generamos un field String nombre con su respectiva Column
    //Como en el caso anterior no puede ser NULL, además no puede ser un String vacío ""
    //Se valida también con "Pattern" en este caso se valida que el nombre solo tenga
    //letras y, además opcionalmente puede contener un segundo nombre y además debe
    //contener al menos 3 letras;
    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]+(\\s[a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]+){0,2}$", message = "Solo se permiten letras en el campo 'nombre'")
    @NotNull(message = "El campo nombre es obligatorio")
    private String nombre;

    //Generamos un field String apellido con su respectivo Column
    //Tendrá en este caso las mismas validaciones que el nombre;
    @Column(nullable = false)
    @NotBlank(message = "El apellido no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]+(\\s[a-zA-ZñÑáéíóúüÁÉÍÓÚÜ]+){0,2}$", message = "Solo se permiten letras en el campo 'apellido'")
    @NotNull(message = "El campo apellido es obligatorio")
    private String apellido;

    //Generamos un fiel String email con su respectiva Column
    //Validamos que no tenga un String vacío y que no ingrese un valor NULL
    //Así mismo usamos la annotation Email para que Springboot utilice su validación
    //Considero que la validación provista por Springboot es algo... "insatisfactoria" para el caso
    //Así que decidí agregarle un regexp propio. que valida el formato "damian@algo.com" o similar.
    //El tema con @Email de Springboot es que para él "damian@damian" es válido y no creo que sirva
    //Para el caso planteado por el ejercicio.
    @Column(nullable = false)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(regexp = "^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "El email ingresado no es correcto.")
    @NotNull(message = "El campo email es obligatorio")
    private String email;

    //Definimos un field LocalDate fechaNacimiento con su respectiva Column
    //Como en los anteriores casos validamos que no llegue un dato NULL
    //Además definimos el formato del Date en este caso como lo solicita
    //El ejercicio tendrá el formato "yyyy-MM-dd"
    //Definimos el mismo formato para el JSON
    //Además usamos la validation @Past para que no se puedan ingresar
    //Valores de fecha posteriores al día de la fecha
    //Adicional a esto creamos una custom annotation llamada Adult que se explicará
    //Con mayor detalle en su respectivo archivo pero que a grandes rasgos valida
    //Que la fecha ingresada manifieste que el Empleado instanciado es mayor de 18 años.
    @Column(nullable = false)
    @NotNull(message = "El campo fechaNacimiento es obligatorio")
    //@DateTimeFormat(pattern = "yyyy-mm-dd")
    //@JsonFormat(pattern="yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    @Adult
    private LocalDate fechaNacimiento;

    //Definimos un field LocalDate fechaIngreso con su respectiva Column
    //Nuevamente no puede contener datos NULL
    //Define nuevamente también el formato de la fecha
    //Y además debe ser @PastOrPresent es decir que solo se pueden ingresar valores
    //De fecha que sean anteriores o el mismo día de la instanciación
    @NotNull(message = "El campo fechaIngreso es obligatorio")
    @Column(nullable = false)
    //@DateTimeFormat(pattern = "yyyy-mm-dd")
    //@JsonFormat(pattern="yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    private LocalDate fechaIngreso;

    //Generamos tambien un field LocalDate con el formato ya definido y con su respectiva
    //Column
    @Column
    //@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaCreacion;

    //Como fecha de creación no recibe datos de la request utilizamos la Annotation
    //PrePersist
    //La anotación @PrePersist en Spring Boot se utiliza para marcar un método que debe
    //Ejecutarse antes de que una nueva entidad se persista (guarde) en la base de datos por primera vez.
    //Lo utilizamos para establecer un valor predeterminado de fecha
    //En fechaCreacion antes de que se guarde en la base de datos.
    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDate.now();
    }
}
