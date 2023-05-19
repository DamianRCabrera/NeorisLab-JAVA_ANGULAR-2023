package com.neoris.turnosrotativos.validations.annotations;

import com.neoris.turnosrotativos.validations.validators.AdultValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Creamos una annotation custom llamada @Adult
// Establacemos que va a estar validada por la clase "AdultValidator" que se detallará en su arch. pertinente.
// La politica de retención será en Runtime.
// El target será un field

// Se define un message default.
// Los métodos groups() y payload() son usados para agrupar la anotación con otras annotations y proveer
// metadata adicional, respectivamente.

@Constraint(validatedBy = AdultValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Adult {
    String message() default "La edad del empleado no puede ser menor a 18 años.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
