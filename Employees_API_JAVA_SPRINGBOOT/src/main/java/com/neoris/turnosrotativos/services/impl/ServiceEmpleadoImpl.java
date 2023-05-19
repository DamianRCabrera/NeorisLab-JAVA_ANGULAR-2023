package com.neoris.turnosrotativos.services.impl;

import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.ConflictRequestException;
import com.neoris.turnosrotativos.exceptions.ResourceNotFoundException;
import com.neoris.turnosrotativos.repository.RepositoryEmpleado;
import com.neoris.turnosrotativos.services.ServiceEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//Definimos que esta clase es un Service con la Annotation @Service
//Implementa la Interface ServiceEmpleado

@Service
public class ServiceEmpleadoImpl implements ServiceEmpleado {

    //Conectamos el ServiceImpl con el repositorio de Empleado
    @Autowired
    RepositoryEmpleado repositoryEmpleado;

    //Este método lo que hará es guardar el Empleado.
    //Chequeamos que el nroDocumento no esté ya en la base de datos
    //Chequeamos que el mail no se encuentre ya en la base de datos
    //En tales casos, throweamos la exception correspondiente
    //Si no hay errores de ese tipo guardaremos el empleado en la db. Y retornamos el objeto guardado.
    @Override
    public Empleado saveEmpleado(Empleado employee) {
        Integer nroDocumento = employee.getNroDocumento();
        String email = employee.getEmail();

        if(repositoryEmpleado.existsByNroDocumento(nroDocumento)){
            throw new ConflictRequestException("Ya existe un empleado con el nroDocumento ingresado.");
        }

        if(repositoryEmpleado.existsByEmail(email)){
            throw new ConflictRequestException("Ya existe un empleado con el email ingresado.");
        }

        return repositoryEmpleado.save(employee);
    }

    //Retornamos la lista completa de todos los empleados.
    @Override
    public List<Empleado> getEmpleadoList() {
        return repositoryEmpleado.findAll();
    }

    //Con el Id buscamos ese empleado si es que existe, sino se retornará NULL
    //Si es NULL throweamos una Exception de que no se encontró el empleado.
    //Caso contrario retornamos el empleado hallado.
    @Override
    public Empleado getEmpleadoById(Integer id) {
        Empleado employee = repositoryEmpleado.findById(id).orElse(null);

        if(employee == null){
            throw new ResourceNotFoundException("No se encontró el empleado con Id: " + id);
        }

        return employee;
    }

    //En este método borramos directamente el empleado por el Id
    @Override
    public void deleteEmpleadoById(Integer id) {
        repositoryEmpleado.deleteById(id);
    }

    //En este método vamos a recibir un Id y un Empleado updateado
    //Vamos a estar retornando el resultado de:
    // 1) Buscar el empleado por el Id
    // 2) En caso de encontrarlo lo mapearemos
    // 3) Extraemos el nroDocumento del updatedEmpleado y su email.
    // 4) Si el empleado encontrado por Id existe por nroDocumento pero el dni del encontrado es diferente al del Updateado
    //tiraremos una Exception de tipo 409. Porque ya existe un empleado en la db con ese nroDocumento.
    // 5) La misma lógica aplicamos para el caso del Email.
    // 6) En otro caso, setearemos las propiedaes adecuadas al empleado encontrado con los valores pasados en el empleado actualizado
    // 7) Por último lo guardamos.
    // 8) Encaso de no encontrar al empleado throweamos una exception de recurso no encontrado.
    @Override
    public Empleado updateEmpleadoById(Integer id, Empleado updatedEmployee) {
        return repositoryEmpleado.findById(id)
                .map(employee -> {
                    Integer nroDocumento = updatedEmployee.getNroDocumento();
                    String email = updatedEmployee.getEmail();

                    if(repositoryEmpleado.existsByNroDocumento(nroDocumento) && !Objects.equals(employee.getNroDocumento(), updatedEmployee.getNroDocumento())){
                        throw new ConflictRequestException("Ya existe un empleado con el nroDocumento ingresado.");
                    }

                    if(repositoryEmpleado.existsByEmail(email) && !(Objects.equals(employee.getEmail(), updatedEmployee.getEmail()))){
                        throw new ConflictRequestException("Ya existe un empleado con el email ingresado.");
                    }

                    employee.setNroDocumento(updatedEmployee.getNroDocumento());
                    employee.setNombre(updatedEmployee.getNombre());
                    employee.setApellido(updatedEmployee.getApellido());
                    employee.setEmail(updatedEmployee.getEmail());
                    employee.setFechaNacimiento(updatedEmployee.getFechaNacimiento());
                    employee.setFechaIngreso(updatedEmployee.getFechaIngreso());
                    return repositoryEmpleado.save(employee);
                })
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el empleado con Id: " + id));
    }
}
