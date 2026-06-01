
package ec.edu.espe.zonas.dtos;

import ec.edu.espe.zonas.entidades.EstadoEspacio;
import ec.edu.espe.zonas.entidades.TipoEspacio;
import ec.edu.espe.zonas.entidades.TipoZona;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspacioRequestDto {

    private String codigo;

    @NotNull(message = "El id de la zona es obligatorio")
    @NotBlank(message = "El id de la zona no puede estar vacío")
    private UUID idZona;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "El tipo de espacio es obligatorio")
    private TipoEspacio tipo;

    @Enumerated(EnumType.STRING)
    private EstadoEspacio estado;
}
