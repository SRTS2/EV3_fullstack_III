package com.rednorte.reasignacion.repository;

import com.rednorte.reasignacion.model.entity.Reasignacion;
import com.rednorte.reasignacion.model.enums.EstadoReasignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReasignacionRepository extends JpaRepository<Reasignacion, Long> {

    List<Reasignacion> findByPacienteId(Long pacienteId);

    List<Reasignacion> findByEstado(EstadoReasignacion estado);

    List<Reasignacion> findByFechaReasignadaBetween(LocalDateTime inicio, LocalDateTime fin);
}
