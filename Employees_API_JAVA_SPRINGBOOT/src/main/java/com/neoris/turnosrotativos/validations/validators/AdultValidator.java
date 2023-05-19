package com.neoris.turnosrotativos.validations.validators;

import com.neoris.turnosrotativos.validations.annotations.Adult;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

// Esta clase AdultValidator implementa la interface ConstraintValidator pasandole dentro Adult que será la annotation
// utilizada llamada Adult y el tipo de dato que en este caso será un LocalDate.

// Sobreescribimos el método isValid al que le vamos a estar pasando un LocalDate y el contexto del Constraint Validator
// Si la LocalDate es NULL retornamos que es válido para que esa cuestión la atrape la Entity
// Luego, si no es Null, nos fijamos la diferencia o período entre ambas fechas y especificamente los años.
// Si es mayor o igual a 18 será válido. Sino no.

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return true;
        }
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();
        return age >= 18;
    }
}
