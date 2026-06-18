package com.rednorte.pacientes.service;

import com.rednorte.pacientes.dto.PacienteDTO;
import com.rednorte.pacientes.model.entity.Paciente;
import com.rednorte.pacientes.repository.PacienteRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PacienteDTO registrar(PacienteDTO datos, String contrasena) {
        if (pacienteRepository.existsByRut(datos.getRut())) {
            throw new IllegalArgumentException("El RUT ya esta registrado");
        }

        Paciente paciente = new Paciente();
        paciente.setRut(datos.getRut());
        paciente.setNombre(datos.getNombre());
        paciente.setEmail(datos.getEmail());
        paciente.setTelefono(datos.getTelefono());
        paciente.setDireccion(datos.getDireccion());
        paciente.setFechaNacimiento(datos.getFechaNacimiento());
        paciente.setContrasena(passwordEncoder.encode(contrasena));
        paciente.setRole("PACIENTE");
        paciente.setActivo(true);

        paciente = pacienteRepository.save(paciente);
        return toDTO(paciente);
    }

    public PacienteDTO autenticar(String rut, String contrasena) {
        Paciente paciente = pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas"));

        if (!paciente.getActivo()) {
            throw new IllegalStateException("Paciente desactivado");
        }

        if (!passwordEncoder.matches(contrasena, paciente.getContrasena())) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }

        return toDTO(paciente);
    }

    public List<PacienteDTO> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public PacienteDTO obtenerPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con id: " + id));
        return toDTO(paciente);
    }

    public PacienteDTO obtenerPorRut(String rut) {
        Paciente paciente = pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con rut: " + rut));
        return toDTO(paciente);
    }

    public PacienteDTO actualizar(Long id, PacienteDTO datos) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con id: " + id));

        if (datos.getNombre() != null) paciente.setNombre(datos.getNombre());
        if (datos.getEmail() != null) paciente.setEmail(datos.getEmail());
        if (datos.getTelefono() != null) paciente.setTelefono(datos.getTelefono());
        if (datos.getDireccion() != null) paciente.setDireccion(datos.getDireccion());
        if (datos.getFechaNacimiento() != null) paciente.setFechaNacimiento(datos.getFechaNacimiento());

        paciente = pacienteRepository.save(paciente);
        return toDTO(paciente);
    }

    public void desactivar(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con id: " + id));
        paciente.setActivo(false);
        pacienteRepository.save(paciente);
    }

    private PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setRut(paciente.getRut());
        dto.setNombre(paciente.getNombre());
        dto.setEmail(paciente.getEmail());
        dto.setTelefono(paciente.getTelefono());
        dto.setDireccion(paciente.getDireccion());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        return dto;
    }
}
