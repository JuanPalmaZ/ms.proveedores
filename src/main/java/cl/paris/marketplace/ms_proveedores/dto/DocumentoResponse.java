package cl.paris.marketplace.ms_proveedores.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.paris.marketplace.ms_proveedores.model.enums.EstadoDocumento;

public record DocumentoResponse(
        UUID id,
        String tipoDocumento,
        LocalDateTime fechaSubida,
        EstadoDocumento estado
) {}
