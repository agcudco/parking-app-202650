package ec.edu.espe.zonas.dtos;

import ec.edu.espe.zonas.entidades.Espacio;
import ec.edu.espe.zonas.entidades.TipoZona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZonaRespondeDto {

    private UUID idZona;
    private String nombre;

    private String codigo;

    private String descripcion;

    private int estado;  //1: activo - 0: inactivo

    private TipoZona tipo;

    private List<Espacio> espacios;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;

    private int capacidad;
}
