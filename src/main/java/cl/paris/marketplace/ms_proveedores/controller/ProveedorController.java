package cl.paris.marketplace.ms_proveedores.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.paris.marketplace.ms_proveedores.dto.DocumentoRequest;
import cl.paris.marketplace.ms_proveedores.dto.DocumentoResponse;
import cl.paris.marketplace.ms_proveedores.dto.ProveedorCompletoResponse;
import cl.paris.marketplace.ms_proveedores.dto.ProveedorRequest;
import cl.paris.marketplace.ms_proveedores.dto.ProveedorResponse;
import cl.paris.marketplace.ms_proveedores.service.ProveedorService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Endpoints para la gestión de proveedores, documentos y vistas consolidadas")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    // ==========================================
    // ENDPOINTS: PROVEEDORES
    // ==========================================

    @Operation(summary = "Crea un nuevo proveedor asociado al usuario autenticado")
    @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Payload con los datos del proveedor",
        content = @Content(
            examples = @ExampleObject(
                name = "EjemploProveedor",
                value = "{\n  \"razonSocial\": \"Importadora y Comercializadora SPA\",\n  \"rut\": \"76.543.210-K\"\n}"
            )
        )
    )
    @PreAuthorize("hasRole('PROVEEDOR') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProveedorResponse> crearProveedor(
            @Valid @RequestBody ProveedorRequest request,
            Authentication authentication
    ) {
        UUID usuarioId = UUID.fromString(authentication.getCredentials().toString());
        ProveedorResponse response = proveedorService.crearProveedor(request, usuarioId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Lista los proveedores asociados a un ID de usuario específico")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida exitosamente")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROVEEDOR') and #usuarioId.toString() == authentication.credentials)")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ProveedorResponse>> listarProveedoresPorUsuario(@PathVariable UUID usuarioId) {
        List<ProveedorResponse> response = proveedorService.listarProveedoresPorUsuario(usuarioId);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Obtiene el ID de proveedor a partir del ID de usuario (Uso interno para ms-productos)")
    @ApiResponse(responseCode = "200", description = "ID del proveedor retornado exitosamente")
    @GetMapping("/interno/usuario/{usuarioId}/id")
    public ResponseEntity<UUID> obtenerIdProveedorInterno(@PathVariable UUID usuarioId) {
        UUID proveedorId = proveedorService.obtenerIdProveedorPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proveedorId);
    }

    // ==========================================
    // ENDPOINTS: DOCUMENTOS
    // ==========================================

    @Operation(summary = "Agrega un nuevo documento al proveedor asociado al usuario autenticado")
    @ApiResponse(responseCode = "201", description = "Documento agregado exitosamente")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Payload con los datos del documento",
        content = @Content(
            examples = @ExampleObject(
                name = "EjemploDocumento",
                value = "{\n  \"tipoDocumento\": \"CONTRATO_COMERCIAL\"\n}"
            )
        )
    )
    @PreAuthorize("hasRole('PROVEEDOR') or hasRole('ADMIN')")
    @PostMapping("/documentos")
    public ResponseEntity<DocumentoResponse> agregarDocumento(
            @Valid @RequestBody DocumentoRequest request,
            Authentication authentication
    ) {
        UUID usuarioId = UUID.fromString(authentication.getCredentials().toString());
        DocumentoResponse response = proveedorService.agregarDocumento(request, usuarioId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ==========================================
    // ENDPOINTS: VISTA CONSOLIDADA
    // ==========================================

    @Operation(summary = "Obtiene la información consolidada de un proveedor por su ID")
    @ApiResponse(responseCode = "200", description = "Vista completa del proveedor obtenida exitosamente")
    @PreAuthorize("hasRole('PROVEEDOR') or hasRole('ADMIN')")
    @GetMapping("/{id}/completo")
    public ResponseEntity<ProveedorCompletoResponse> obtenerProveedorCompleto(@PathVariable UUID id) {
        ProveedorCompletoResponse response = proveedorService.obtenerProveedorCompleto(id);
        return ResponseEntity.ok(response);
    }
}