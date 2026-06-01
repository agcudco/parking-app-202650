package ec.edu.espe.zonas.services.impl;

import ec.edu.espe.zonas.dtos.EspacioRequestDto;
import ec.edu.espe.zonas.dtos.EspacioResponseDto;
import ec.edu.espe.zonas.entidades.Espacio;
import ec.edu.espe.zonas.entidades.EstadoEspacio;
import ec.edu.espe.zonas.entidades.Zona;
import ec.edu.espe.zonas.repositorios.EspacioRepositorio;
import ec.edu.espe.zonas.repositorios.ZonaRepositorio;
import ec.edu.espe.zonas.services.EspacioServicio;
import ec.edu.espe.zonas.utils.UtilsMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspacioServicioImpl implements EspacioServicio {

    private final EspacioRepositorio repositorioEspacio;
    private final ZonaRepositorio zonaRepositorio;
    private final UtilsMappers mapper;


    @Override
    @Transactional(readOnly = true)
    public List<EspacioResponseDto> obtenerEspacios() {
        return repositorioEspacio.findAll().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EspacioResponseDto crearEspacio(EspacioRequestDto dto) {

        Zona objZona = zonaRepositorio.findById(dto.getIdZona())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con id: " + dto.getIdZona()));

        Espacio nuevoEspacio = mapper.toEntityEspacio(dto);
        nuevoEspacio.setZona(objZona);
        nuevoEspacio.setEstado(EstadoEspacio.DISPONIBLE); // Por defecto, el nuevo espacio estará disponible

        Espacio espacioSaved = repositorioEspacio.save(nuevoEspacio);

        return mapper.toResponseDto(espacioSaved);
    }

    @Override
    public EspacioResponseDto actualizarEspacio(UUID idEspacio, EspacioRequestDto dto) {

        if (!repositorioEspacio.existsById(idEspacio))
            throw new RuntimeException("No se encontró el espacio con id: " + idEspacio);

        Zona objZona = zonaRepositorio.findById(dto.getIdZona())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con id: " + dto.getIdZona()));

        Espacio newEspacio = mapper.toEntityEspacio(dto);
        newEspacio.setZona(objZona);

        return mapper.toResponseDto(repositorioEspacio.save(newEspacio));
    }

    @Override
    public void eliminarEspacio(UUID idEspacio) {
        Espacio espacioAnt = repositorioEspacio.findById(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con id: " + idEspacio));
        if (!espacioAnt.isActivo())
            throw new RuntimeException("Espacio ya se encuentra inactivo");

        espacioAnt.setActivo(false);

        repositorioEspacio.save(espacioAnt);

    }

    @Override
    public EspacioResponseDto cambiarEstado(UUID idEspacio, EstadoEspacio estado) {

        Espacio espacioAnt = repositorioEspacio.findById(idEspacio)
                .orElseThrow(() -> new RuntimeException("Espacio no encontrado con id: " + idEspacio));

        if (espacioAnt.getEstado().equals(estado.name()))
            throw new RuntimeException("El espacio ya se encuentra en el estado: " + estado);

        espacioAnt.setEstado(estado);

        return mapper.toResponseDto(repositorioEspacio.save(espacioAnt));
    }

    @Override
    public List<EspacioResponseDto> obtenerEspaciosPorEstado(EstadoEspacio estado) {
        return repositorioEspacio.findByEstado(estado.name()).stream()
                .map((espacio) -> mapper.toResponseDto(espacio))
                .collect(Collectors.toList());
    }

    @Override
    public List<EspacioResponseDto> obtenerEspaciosPorZonaEstado(UUID idZona, EstadoEspacio estado) {
        if (!zonaRepositorio.existsById(idZona)) throw new RuntimeException("Zona no encontrada con id: " + idZona);

        return repositorioEspacio.findByZonaAndEstado(idZona, estado.name()).stream()
                .map((espacio) -> mapper.toResponseDto(espacio))
                .collect(Collectors.toList());
    }
}
