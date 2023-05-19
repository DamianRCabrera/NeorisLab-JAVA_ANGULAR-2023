package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.entities.Empleado;

import java.util.List;

//Interface para definir métodos de lo que será el Service para Empleados


public interface ServiceEmpleado {
    Empleado saveEmpleado(Empleado employee);

    List<Empleado> getEmpleadoList();

    Empleado getEmpleadoById(Integer id);

    void deleteEmpleadoById(Integer id);

    Empleado updateEmpleadoById(Integer id, Empleado newEmployee);
}
