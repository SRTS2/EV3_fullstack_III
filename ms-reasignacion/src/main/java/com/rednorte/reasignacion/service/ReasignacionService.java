package com.rednorte.reasignacion.service;

import com.rednorte.reasignacion.dto.ReasignacionDTO;
import com.rednorte.reasignacion.model.entity.Reasignacion;
import com.rednorte.reasignacion.model.enums.EstadoReasignacion;
import com.rednorte.reasignacion.repository.ReasignacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReasignacionService {

    private final ReasignacionRepository repository;

    @Transactional
    public ReasignacionDTO ejecutarReasignacion(ReasignacionDTO dto) {
        Reasignacion reasignacion = Reasignacion.builder()
                .pacienteId(dto.getPacienteId())
                .nombrePaciente(dto.getNombrePaciente())
                .especialidad(dto.getEspecialidad())
                .fechaOriginal(LocalDateTime.now())
                .fechaReasignada(generarFechaDisponible())
                .estado(EstadoReasignacion.PENDIENTE)
                .build();

        reasignacion = repository.save(reasignacion);
        return toDTO(reasignacion);
    }

    @Transactional(readOnly = true)
    public List<ReasignacionDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReasignacionDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reasignacion no encontrada con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ReasignacionDTO> obtenerHistorial(Long pacienteId) {
        return repository.findByPacienteId(pacienteId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReasignacionDTO> listarDisponibles() {
        return repository.findByEstado(EstadoReasignacion.PENDIENTE)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public ReasignacionDTO actualizar(Long id, ReasignacionDTO dto) {
        Reasignacion entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reasignacion no encontrada con id: " + id));
        if (dto.getNombrePaciente() != null) entity.setNombrePaciente(dto.getNombrePaciente());
        if (dto.getEspecialidad() != null) entity.setEspecialidad(dto.getEspecialidad());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        if (dto.getMotivoCancelacion() != null) entity.setMotivoCancelacion(dto.getMotivoCancelacion());
        if (dto.getFechaReasignada() != null) entity.setFechaReasignada(dto.getFechaReasignada());
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public ReasignacionDTO confirmarReasignacion(Long id) {
        Reasignacion entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reasignacion no encontrada con id: " + id));
        entity.setEstado(EstadoReasignacion.CONFIRMADA);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public ReasignacionDTO cancelarReasignacion(Long id, String motivo) {
        Reasignacion entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reasignacion no encontrada con id: " + id));
        entity.setEstado(EstadoReasignacion.CANCELADA);
        entity.setMotivoCancelacion(motivo);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Reasignacion no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    private LocalDateTime generarFechaDisponible() {
        Random random = new Random();
        int dias = random.nextInt(14) + 1;
        return LocalDateTime.now().plusDays(dias).withHour(10).withMinute(0).withSecond(0).withNano(0);
    }

    private ReasignacionDTO toDTO(Reasignacion entity) {
        return ReasignacionDTO.builder()
                .id(entity.getId())
                .pacienteId(entity.getPacienteId())
                .nombrePaciente(entity.getNombrePaciente())
                .especialidad(entity.getEspecialidad())
                .fechaOriginal(entity.getFechaOriginal())
                .fechaReasignada(entity.getFechaReasignada())
                .estado(entity.getEstado())
                .motivoCancelacion(entity.getMotivoCancelacion())
                .build();
    }
}
