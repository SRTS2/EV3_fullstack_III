package com.rednorte.pacientes.repository;

import com.rednorte.pacientes.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByRut(String rut);

    Optional<Paciente> findByEmail(String email);

    boolean existsByRut(String rut);
}
