package com.neoris.turnosrotativos.services.impl;

import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.exceptions.BadHoursConfigException;
import com.neoris.turnosrotativos.exceptions.ResourceNotFoundException;
import com.neoris.turnosrotativos.repository.RepositoryConcepto;
import com.neoris.turnosrotativos.repository.RepositoryEmpleado;
import com.neoris.turnosrotativos.repository.RepositoryJornada;
import com.neoris.turnosrotativos.dto.JornadaDTO;
import com.neoris.turnosrotativos.services.ServiceJornada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

//Definimos la clase como un Service

@Service
public class ServiceJornadaImpl implements ServiceJornada {

    // Conectamos el service con los 3 repositorios
    @Autowired
    RepositoryJornada repositoryJornada;
    @Autowired
    RepositoryEmpleado repositoryEmpleado;
    @Autowired
    RepositoryConcepto repositoryConcepto;

    //En este método para guardar la Jornada recibimos el DTO de Jornada
    //Implementé métodos para validar los requerimientos de los horarios, conceptos, días etc.

    // Extraemos el Id del empleado del DTO y el Id del concepto ingresado.
    // Buscamos tanto el empleado como el concepto por id con los métodos de sus respectivos repositorios.

    //Si alguno de ellos es NULL throwaremos el exception de que no se pudo hallar.

    //En caso contrario extraeremos el nroDocumento, el nombre del Concepto, su Fecha y las horas trabajadas.
    //Usamos el método del repositorio que checkea si ya existe ese concepto en esa fecha para ese empleado. Si
    //Existe tiraremos el exception correspondiente.

    //Caso contrario si el concepto encontrado tiene nombre "Turno Normal" O "Turno Extra"
    //Usaremos nuestros métodos para validar los requerimientos del UserStory para esos casos, es decir, hsMin y hsMax, etc.

    // En caso de que sea "Dia Libre" validaremos los req. para ese caso, por ejemplo, que no se pasen hsTrabajadas, etc.

    //Si no hay problemas, instanciamos una nueva jornada, seteamos los datos recibidos y guardamos la jornada.
    //Retornaremos el resultado de lo guardado en la DB.
    @Override
    public Jornada saveJornada(JornadaDTO jornadaDTO) {
        Integer idEmpleado = jornadaDTO.getIdEmpleado();
        Integer idConcepto = jornadaDTO.getIdConcepto();

        Empleado foundEmployee = repositoryEmpleado.findById(idEmpleado).orElse(null);
        Concepto foundConcepto = repositoryConcepto.findById(idConcepto).orElse(null);


        if(foundEmployee == null){
            throw new ResourceNotFoundException("No se encontró el empleado con Id: " + idEmpleado);
        }
        if(foundConcepto == null){
            throw new ResourceNotFoundException("No se encontró el concepto con Id: " + idConcepto);
        }

        Integer nroDocumentoEmployeeFound = foundEmployee.getNroDocumento();
        String foundConceptoName = foundConcepto.getNombre();
        LocalDate fechaJornadaDto = jornadaDTO.getFecha();
        Integer hsTrabajadasDto = jornadaDTO.getHorasTrabajadas();

        boolean conceptoAlreadyPresentInThatDay = repositoryJornada.existsByNroDocumentoAndConceptoAndFecha(nroDocumentoEmployeeFound, foundConceptoName, fechaJornadaDto);

        if(conceptoAlreadyPresentInThatDay){
            throw new BadHoursConfigException("El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.");
        }

        if((Objects.equals(foundConcepto.getNombre(), "Turno Normal") || Objects.equals(foundConcepto.getNombre(), "Turno Extra"))){
            checkValidityOfNormalShiftAndExtraShift(jornadaDTO, foundEmployee, foundConcepto);
        }

        if(Objects.equals(foundConcepto.getNombre(), "Dia Libre")){
            checkValidityOfDayOff(jornadaDTO, foundEmployee);
        }

        ////////////////////////////            After validations                         ////////////////////////

        Jornada newJornada = new Jornada();

        newJornada.setNroDocumento(nroDocumentoEmployeeFound);

        String nombreCompleto = foundEmployee.getNombre() + " " + foundEmployee.getApellido();
        newJornada.setNombreCompleto(nombreCompleto);

        newJornada.setConcepto(foundConceptoName);

        newJornada.setFecha(fechaJornadaDto);

        newJornada.setHorasTrabajadas(hsTrabajadasDto);

        return repositoryJornada.save(newJornada);
    }

    // Método para conseguir todas las Jornadas con el método del repositorio
    @Override
    public List<Jornada> getAllJornadas() {
        return repositoryJornada.findAll();
    }

    // Método para conseguir todas las Jornadas por nroDocumento con el método del repositorio
    @Override
    public List<Jornada> getAllByNroDocumento(Integer nroDocumento) {
        return repositoryJornada.findAllByNroDocumento(nroDocumento);
    }

    // Método para conseguir todas las Jornadas por fecha con el método del repositorio
    @Override
    public List<Jornada> getAllByFecha(LocalDate fecha) {
        return repositoryJornada.findAllByFecha(fecha);
    }

    // Método para conseguir todas las Jornadas por nroDocumento y fecha con el método del repositorio
    @Override
    public List<Jornada> getAllByNroDocumentoAndFecha(Integer nroDocumento, LocalDate fecha) {
        return repositoryJornada.findAllByNroDocumentoAndFecha(nroDocumento, fecha);
    }

    //Aquí implementamos la lógica solicitada en la UserStory para las jornadas normales y extras
    public void checkValidityOfNormalShiftAndExtraShift(JornadaDTO jornadaDTO, Empleado foundEmployee, Concepto foundConcepto){
        if(jornadaDTO.getHorasTrabajadas() == null){
            throw new BadHoursConfigException("'hsTrabajadas' es obligatorio para el concepto ingresado.");
        }
        if(jornadaDTO.getHorasTrabajadas() > foundConcepto.getHsMaximo() || jornadaDTO.getHorasTrabajadas() < foundConcepto.getHsMinimo()){
            throw new BadHoursConfigException("El rango de horas que se puede cargar para este concepto es de " + foundConcepto.getHsMinimo() + " - "+ foundConcepto.getHsMaximo());
        }
        if(repositoryJornada.existsByNroDocumentoAndConceptoAndFecha(foundEmployee.getNroDocumento(), "Dia Libre", jornadaDTO.getFecha())){
            throw new BadHoursConfigException("El empleado ingresado cuenta con un día libre en esa fecha.");
        }

        Integer hsTrabajadasThatDay = repositoryJornada.findAllByNroDocumentoAndFecha(foundEmployee.getNroDocumento(), jornadaDTO.getFecha()).stream()
                .mapToInt(Jornada::getHorasTrabajadas)
                .sum();

        if(hsTrabajadasThatDay + jornadaDTO.getHorasTrabajadas() > 12){
            throw new BadHoursConfigException("El empleado no puede cargar más de 12 horas trabajadas en un día.");
        }

        LocalDate startDate = jornadaDTO.getFecha().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusDays(6);

        OptionalInt hsTrabajadasThatWeek = repositoryJornada.findAllCreatedInWeekForNroDocumento(foundEmployee.getNroDocumento(), startDate, endDate)
                .stream()
                .map(Jornada::getHorasTrabajadas)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .reduce(Integer::sum);

        Integer totalHsTrabajadasThatWeek = hsTrabajadasThatWeek.orElse(0);

        if(totalHsTrabajadasThatWeek + jornadaDTO.getHorasTrabajadas() > 48){
            throw new BadHoursConfigException("El empleado ingresado supera las 48 horas semanales.");
        }

        Integer extraShiftInsideTheWeek = repositoryJornada.countAllByNroDocumentoAndConceptoAndFechaBetween(foundEmployee.getNroDocumento(), "Turno Extra", startDate, endDate);

        if(extraShiftInsideTheWeek >= 3 && Objects.equals(foundConcepto.getNombre(), "Turno Extra")){
            throw new BadHoursConfigException("El empleado ingresado ya cuenta con 3 turnos extra esta semana.");
        }

        Integer normalShiftInsideTheWeek = repositoryJornada.countAllByNroDocumentoAndConceptoAndFechaBetween(foundEmployee.getNroDocumento(),"Turno Normal", startDate, endDate);
        if(normalShiftInsideTheWeek >= 5 && Objects.equals(foundConcepto.getNombre(), "Turno Normal")){
            throw new BadHoursConfigException("El empleado ingresado ya cuenta con 5 turnos normales esta semana.");
        }
    }

    //Aquí implementamos la lógica solicitada en la UserStory para las jornada libre
    public void checkValidityOfDayOff(JornadaDTO jornadaDTO, Empleado foundEmployee){
        if(jornadaDTO.getHorasTrabajadas() != null){
            throw new BadHoursConfigException("El concepto ingresado no requiere el ingreso de 'hsTrabajadas'.");
        }
        if(repositoryJornada.existsByNroDocumentoAndFecha(foundEmployee.getNroDocumento(), jornadaDTO.getFecha())){
            throw new BadHoursConfigException("El empleado no puede cargar Dia Libre si cargo un turno previamente para la fecha ingresada");
        }

        LocalDate startDate = jornadaDTO.getFecha().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusDays(6);

        Integer daysOffThisWeek = repositoryJornada.countAllByNroDocumentoAndConceptoAndFechaBetween(foundEmployee.getNroDocumento(),"Dia Libre", startDate, endDate);

        if(daysOffThisWeek >= 2){
            throw new BadHoursConfigException("El empleado no cuenta con más días libres esta semana.");
        }
    }
}
