package com.rednorte.listaespera.service;

import com.rednorte.listaespera.dto.ListaEsperaDTO;
import com.rednorte.listaespera.model.entity.PacienteEspera;
import com.rednorte.listaespera.model.enums.EstadoEspera;
import com.rednorte.listaespera.model.enums.Prioridad;
import com.rednorte.listaespera.repository.PacienteEsperaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListaEsperaService {

    private final PacienteEsperaRepository repository;

    public ListaEsperaService(PacienteEsperaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ListaEsperaDTO registrar(ListaEsperaDTO dto) {
        int tiempoEstimado = switch (dto.getPrioridad()) {
            case URGENTE -> 1;
            case ALTA -> 3;
            case MEDIA -> 7;
            case BAJA -> 15;
        };

        PacienteEspera entity = PacienteEspera.builder()
                .pacienteId(dto.getPacienteId())
                .nombrePaciente(dto.getNombrePaciente())
                .rutPaciente(dto.getRutPaciente())
                .especialidad(dto.getEspecialidad())
                .prioridad(dto.getPrioridad())
                .estado(EstadoEspera.EN_ESPERA)
                .fechaIngreso(LocalDateTime.now())
                .tiempoEstimadoDias(tiempoEstimado)
                .observaciones(dto.getObservaciones())
                .build();
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ListaEsperaDTO> listarPendientes() {
        return repository.findByEstado(EstadoEspera.EN_ESPERA)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ListaEsperaDTO> listarPorEspecialidad(String especialidad) {
        return repository.findByEspecialidadAndEstado(especialidad, EstadoEspera.EN_ESPERA)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ListaEsperaDTO> obtenerPorPaciente(Long pacienteId) {
        return repository.findByPacienteId(pacienteId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ListaEsperaDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
    }

    @Transactional
    public ListaEsperaDTO actualizar(Long id, ListaEsperaDTO dto) {
        PacienteEspera entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
        if (dto.getNombrePaciente() != null) entity.setNombrePaciente(dto.getNombrePaciente());
        if (dto.getRutPaciente() != null) entity.setRutPaciente(dto.getRutPaciente());
        if (dto.getEspecialidad() != null) entity.setEspecialidad(dto.getEspecialidad());
        if (dto.getPrioridad() != null) entity.setPrioridad(dto.getPrioridad());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        if (dto.getObservaciones() != null) entity.setObservaciones(dto.getObservaciones());
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public ListaEsperaDTO actualizarEstado(Long id, EstadoEspera estado) {
        PacienteEspera entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
        entity.setEstado(estado);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entrada no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    private ListaEsperaDTO toDTO(PacienteEspera entity) {
        return ListaEsperaDTO.builder()
                .id(entity.getId())
                .pacienteId(entity.getPacienteId())
                .nombrePaciente(entity.getNombrePaciente())
                .rutPaciente(entity.getRutPaciente())
                .especialidad(entity.getEspecialidad())
                .prioridad(entity.getPrioridad())
                .estado(entity.getEstado())
                .fechaIngreso(entity.getFechaIngreso())
                .tiempoEstimadoDias(entity.getTiempoEstimadoDias())
                .observaciones(entity.getObservaciones())
                .build();
    }
}
