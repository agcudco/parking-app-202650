package ec.edu.espe.zonas.services;

import ec.edu.espe.zonas.dtos.ZonaRequestDto;
import ec.edu.espe.zonas.dtos.ZonaRespondeDto;
import ec.edu.espe.zonas.entidades.Zona;

import java.util.List;
import java.util.UUID;

public interface ZonaServicio {

    List<ZonaRespondeDto> listarZonas();

    ZonaRespondeDto crearZona(ZonaRequestDto request);

    ZonaRespondeDto actualizarZona(UUID idZona, ZonaRequestDto request);

    void activarDesactivar(UUID idZona);
}
