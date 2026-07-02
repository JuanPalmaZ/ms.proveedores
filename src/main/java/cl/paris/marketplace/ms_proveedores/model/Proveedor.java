package cl.paris.marketplace.ms_proveedores.model;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name="razonSocial")
    private String razonSocial;

    @Column(name="rut", unique=true, nullable= true)
    private String rut; 

    @Column(name="estadoApro")
    private String estadoApro;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    // Constructor vacío
    public Proveedor() {
    }

    // Constructor con parámetros
    public Proveedor(UUID id, UUID usuarioId, String razonSocial, String rut, String estadoApro, List<Documento> documentos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.razonSocial = razonSocial;
        this.rut = rut;
        this.estadoApro = estadoApro;
        this.documentos = documentos;
    }

    // ==========================================
    // GETTERS Y SETTERS EXPLÍCITOS
    // ==========================================

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEstadoApro() {
        return estadoApro;
    }

    public void setEstadoApro(String estadoApro) {
        this.estadoApro = estadoApro;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }
}
