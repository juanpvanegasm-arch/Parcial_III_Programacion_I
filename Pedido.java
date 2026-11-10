import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String codigoUnico;
    private LocalDate fecha;
    private Cliente cliente;
    private ModalidadPedido modalidad;
    private EstadoPedido estado;
    private List<DetallePedido> detalles;
    private IEmpresaTransporte empresaTransporte;
    private double distanciaDomicilioKm;
    private double costoDomicilio;
    // Banderas para no usar null:
    private boolean transporteAsignado;
    private boolean servicioAceptado;

    public Pedido(String codigoUnico, LocalDate fecha, Cliente cliente, ModalidadPedido modalidad) {
        this.codigoUnico = codigoUnico;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = EstadoPedido.PENDIENTE;
        this.detalles = new ArrayList<DetallePedido>();
        // Valor por defecto: solo se usa si transporteAsignado es true
        this.empresaTransporte = new RapidoExpress();
        this.distanciaDomicilioKm = 0;
        this.costoDomicilio = 0;
        this.transporteAsignado = false;
        this.servicioAceptado = false;
    }

    // Solo se pueden agregar productos mientras el pedido está pendiente
    public void agregarDetalle(Producto producto, int cantidad) {
        if (estado == EstadoPedido.PENDIENTE) {
            detalles.add(new DetallePedido(producto, cantidad));
        }
    }

    public void asignarTransporte(IEmpresaTransporte empresa, double distanciaKm) {
        if (modalidad == ModalidadPedido.DOMICILIO && estado == EstadoPedido.PENDIENTE) {
            this.empresaTransporte = empresa;
            this.distanciaDomicilioKm = distanciaKm;
            this.transporteAsignado = true;
            this.servicioAceptado = empresa.aceptarServicio(distanciaKm);
            if (servicioAceptado) {
                this.costoDomicilio = empresa.calcularCostoEnvio(distanciaKm);
            } else {
                this.costoDomicilio = 0;
            }
        }
    }

    // Devuelve "" si el pedido se puede confirmar; si no, el motivo del rechazo
    public String obtenerMotivoRechazo(List<Producto> menuRestaurante) {
        if (!cliente.isRegistrado()) {
            return "El cliente no está registrado en el restaurante.";
        }
        if (detalles.size() == 0) {
            return "El pedido no tiene productos.";
        }
        for (int i = 0; i < detalles.size(); i++) {
            DetallePedido d = detalles.get(i);
            if (d.getCantidad() <= 0) {
                return "La cantidad de '" + d.getProducto().getNombre() + "' debe ser mayor que cero.";
            }
            boolean enMenu = false;
            for (int j = 0; j < menuRestaurante.size(); j++) {
                if (menuRestaurante.get(j).getCodigo().equals(d.getProducto().getCodigo())) {
                    enMenu = true;
                }
            }
            if (!enMenu) {
                return "El producto '" + d.getProducto().getNombre() + "' no pertenece al menú.";
            }
            if (!d.getProducto().verificarCondicionesCalidad()) {
                return "El producto '" + d.getProducto().getNombre()
                        + "' no cumple las condiciones de calidad.";
            }
        }
        if (modalidad == ModalidadPedido.DOMICILIO) {
            if (!transporteAsignado) {
                return "No se ha solicitado una empresa de transporte para el domicilio.";
            }
            if (!servicioAceptado) {
                return "La empresa " + empresaTransporte.getNombreEmpresa()
                        + " no aceptó el servicio (distancia fuera de cobertura).";
            }
        }
        return "";
    }

    public boolean verificarRequisitosConfirmacion(List<Producto> menuRestaurante) {
        return obtenerMotivoRechazo(menuRestaurante).equals("");
    }

    public boolean confirmarPedido(List<Producto> menuRestaurante) {
        if (estado != EstadoPedido.PENDIENTE) {
            return false; // evita confirmaciones repetidas
        }
        if (!verificarRequisitosConfirmacion(menuRestaurante)) {
            return false;
        }
        estado = EstadoPedido.CONFIRMADO;
        return true;
    }

    // Valor de los productos solamente (sin domicilio)
    public double calcularValorProductos() {
        double total = 0;
        for (int i = 0; i < detalles.size(); i++) {
            total = total + detalles.get(i).calcularSubtotal();
        }
        return total;
    }

    public double calcularValorTotalPedido() {
        return calcularValorProductos() + costoDomicilio;
    }

    /*
     * Reglas de cambio de estado:
     * - CONFIRMADO solo se logra con confirmarPedido().
     * - ENTREGADO solo desde CONFIRMADO.
     * - CANCELADO solo desde PENDIENTE o CONFIRMADO.
     * - Un pedido cancelado o entregado ya no cambia.
     */
    public void actualizarEstado(EstadoPedido nuevoEstado) {
        if (estado == EstadoPedido.CANCELADO || estado == EstadoPedido.ENTREGADO) {
            return;
        }
        if (nuevoEstado == EstadoPedido.ENTREGADO && estado == EstadoPedido.CONFIRMADO) {
            estado = EstadoPedido.ENTREGADO;
        } else if (nuevoEstado == EstadoPedido.CANCELADO) {
            estado = EstadoPedido.CANCELADO;
        }
    }

    // Un pedido cuenta como venta si está confirmado o entregado
    public boolean cuentaComoIngreso() {
        return estado == EstadoPedido.CONFIRMADO || estado == EstadoPedido.ENTREGADO;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public ModalidadPedido getModalidad() {
        return modalidad;
    }

    public double getCostoDomicilio() {
        return costoDomicilio;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public IEmpresaTransporte getEmpresaTransporte() {
        return empresaTransporte;
    }

    public double getDistanciaDomicilioKm() {
        return distanciaDomicilioKm;
    }

    public boolean isTransporteAsignado() {
        return transporteAsignado;
    }

    public boolean isServicioAceptado() {
        return servicioAceptado;
    }
}