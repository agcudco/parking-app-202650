package ec.edu.espe.zonas.services.impl;

import ec.edu.espe.zonas.dtos.ZonaRequestDto;
import ec.edu.espe.zonas.dtos.ZonaRespondeDto;
import ec.edu.espe.zonas.entidades.Zona;
import ec.edu.espe.zonas.repositorios.ZonaRepositorio;
import ec.edu.espe.zonas.services.ZonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ZonaServicioImpl implements ZonaServicio {

    @Autowired
    private ZonaRepositorio repositorioZona;

    @Override
    @Transactional(readOnly = true)
    public List<ZonaRespondeDto> listarZonas() {
        return repositorioZona.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ZonaRespondeDto crearZona(ZonaRequestDto request) {

        if (repositorioZona.existsByNombre(request.getNombre()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "YA EXISTEN EL NOMBRE");

        Zona objZona = new Zona();

        objZona.setNombre(request.getNombre());
        objZona.setCodigo(generarCodigo(request));
        objZona.setDescripcion(request.getDescripcion());
        objZona.setCapacidad(request.getCapacidad());
        objZona.setTipo(request.getTipo());

        repositorioZona.save(objZona);

        return toResponse(objZona);
    }

    @Override
    public ZonaRespondeDto actualizarZona(UUID idZona, ZonaRequestDto request) {
        return null;
    }

    @Override
    public void activarDesactivar(UUID idZona) {

    }

    private ZonaRespondeDto toResponse(Zona objZona) {
        return ZonaRespondeDto.builder()
                .idZona(objZona.getId())
                .nombre(objZona.getNombre())
                .codigo(objZona.getCodigo())
                .descripcion(objZona.getDescripcion())
                .tipo(objZona.getTipo())
                .capacidad(objZona.getCapacidad())
                .espacios(objZona.getEspacios())
                .estado(objZona.getEstado())
                .fechaCreacion(objZona.getFechaCreacion())
                .fechaModificacion(objZona.getFechaModificacion())
                .build();
    }

    private String generarCodigo(ZonaRequestDto request) {
        return "Mi codigo generado"; //ZONA TIPO NUMERO - ZON-REG-01
    }
}
