import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String numeroDocumento;
    private String nombreCompleto;
    private String telefono;
    private String correoElectronico;
    private String direccionResidencia;
    private List<Pedido> historialPedidos;
    // Bandera: la pone en true el restaurante cuando registra al cliente
    private boolean registrado;

    public Cliente(String numeroDocumento, String nombreCompleto, String telefono,
                   String correoElectronico, String direccionResidencia) {
        this.numeroDocumento = numeroDocumento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.direccionResidencia = direccionResidencia;
        this.historialPedidos = new ArrayList<Pedido>();
        this.registrado = false;
    }

    public void agregarPedidoAlHistorial(Pedido pedido) {
        historialPedidos.add(pedido);
    }

    public List<Pedido> consultarHistorial() {
        return historialPedidos;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getDireccionResidencia() {
        return direccionResidencia;
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }
}