package ec.edu.espe.zonas.dtos;

import ec.edu.espe.zonas.entidades.TipoEspacio;
import ec.edu.espe.zonas.entidades.Zona;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspacioResponseDto {

    private UUID id;

    private String codigo;//nombre

    private String descripcion;

    private TipoEspacio tipo;

    private boolean estado;

    private UUID idZona;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;
}
