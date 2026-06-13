package com.rednorte.listaespera.repository;

import com.rednorte.listaespera.model.entity.PacienteEspera;
import com.rednorte.listaespera.model.enums.EstadoEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteEsperaRepository extends JpaRepository<PacienteEspera, Long> {

    List<PacienteEspera> findByEstado(EstadoEspera estado);

    List<PacienteEspera> findByPacienteId(Long pacienteId);

    List<PacienteEspera> findByEspecialidadAndEstado(String especialidad, EstadoEspera estado);

    long countByEstado(EstadoEspera estado);
}
