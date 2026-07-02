package cl.paris.marketplace.ms_proveedores.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarEstadoDocumentoRequest(

    @NotBlank(message = "El estado es obligatorio")
    String estado

) {}
