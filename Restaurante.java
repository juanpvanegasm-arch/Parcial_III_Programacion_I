import java.util.ArrayList;
import java.util.List;

public class Restaurante {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private List<Producto> menu;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;

    public Restaurante(String nombreComercial, String nit, String direccion) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.menu = new ArrayList<Producto>();
        this.clientes = new ArrayList<Cliente>();
        this.pedidos = new ArrayList<Pedido>();
    }

    // ---------- Búsquedas (devuelven la posición, o -1 si no existe) ----------
    public int buscarIndiceProducto(String codigo) {
        int posicion = -1;
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getCodigo().equals(codigo)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public int buscarIndiceCliente(String documento) {
        int posicion = -1;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNumeroDocumento().equals(documento)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public int buscarIndicePedido(String codigo) {
        int posicion = -1;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getCodigoUnico().equals(codigo)) {
                posicion = i;
            }
        }
        return posicion;
    }

    // ---------- Registros ----------
    public boolean registrarProducto(Producto producto) {
        if (buscarIndiceProducto(producto.getCodigo()) != -1) {
            return false; // código duplicado
        }
        menu.add(producto);
        return true;
    }

    public boolean registrarCliente(Cliente cliente) {
        if (buscarIndiceCliente(cliente.getNumeroDocumento()) != -1) {
            return false; // documento duplicado
        }
        cliente.setRegistrado(true);
        clientes.add(cliente);
        return true;
    }

    // Registra el pedido en el restaurante y en el historial del cliente
    public void registrarPedido(Pedido pedido) {
        if (buscarIndicePedido(pedido.getCodigoUnico()) == -1) {
            pedidos.add(pedido);
            pedido.getCliente().agregarPedidoAlHistorial(pedido);
        }
    }

    // ---------- Consultas ----------
    public List<Producto> consultarProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<Producto>();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).verificarCondicionesCalidad()) {
                disponibles.add(menu.get(i));
            }
        }
        return disponibles;
    }

    public List<Producto> consultarProductosRechazados() {
        List<Producto> rechazados = new ArrayList<Producto>();
        for (int i = 0; i < menu.size(); i++) {
            if (!menu.get(i).verificarCondicionesCalidad()) {
                rechazados.add(menu.get(i));
            }
        }
        return rechazados;
    }

    // ---------- Reportes ----------
    // Ingresos propios del restaurante: solo productos de pedidos confirmados o entregados
    public double calcularIngresosRestaurante() {
        double total = 0;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).cuentaComoIngreso()) {
                total = total + pedidos.get(i).calcularValorProductos();
            }
        }
        return total;
    }

    // Valores cobrados por domicilios (se muestran aparte)
    public double calcularIngresosDomicilios() {
        double total = 0;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).cuentaComoIngreso()) {
                total = total + pedidos.get(i).getCostoDomicilio();
            }
        }
        return total;
    }

    public int contarUnidadesVendidas(String codigoProducto) {
        int unidades = 0;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).cuentaComoIngreso()) {
                List<DetallePedido> detalles = pedidos.get(i).getDetalles();
                for (int j = 0; j < detalles.size(); j++) {
                    if (detalles.get(j).getProducto().getCodigo().equals(codigoProducto)) {
                        unidades = unidades + detalles.get(j).getCantidad();
                    }
                }
            }
        }
        return unidades;
    }

    // Bandera para saber si ya hay productos vendidos
    public boolean hayVentas() {
        boolean hay = false;
        for (int i = 0; i < menu.size(); i++) {
            if (contarUnidadesVendidas(menu.get(i).getCodigo()) > 0) {
                hay = true;
            }
        }
        return hay;
    }

    // Si no hay ventas devuelve un producto "vacío" (usar antes hayVentas())
    public Producto obtenerProductoMasVendido() {
        int mejorPosicion = -1;
        int mejorUnidades = 0;
        for (int i = 0; i < menu.size(); i++) {
            int unidades = contarUnidadesVendidas(menu.get(i).getCodigo());
            if (unidades > mejorUnidades) {
                mejorUnidades = unidades;
                mejorPosicion = i;
            }
        }
        if (mejorPosicion == -1) {
            return new PlatoFuerte("SIN-VENTAS", "Sin ventas", "", 0, 0, 0,
                    TipoPreparacionPlato.ASADO, false);
        }
        return menu.get(mejorPosicion);
    }

    public double calcularIngresosPorCategoria(String categoria) {
        double total = 0;
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).cuentaComoIngreso()) {
                List<DetallePedido> detalles = pedidos.get(i).getDetalles();
                for (int j = 0; j < detalles.size(); j++) {
                    if (detalles.get(j).getProducto().getCategoria().equals(categoria)) {
                        total = total + detalles.get(j).calcularSubtotal();
                    }
                }
            }
        }
        return total;
    }

    public String obtenerCategoriaConMayorIngreso() {
        double platos = calcularIngresosPorCategoria("Plato fuerte");
        double bebidas = calcularIngresosPorCategoria("Bebida");
        double postres = calcularIngresosPorCategoria("Postre");

        if (platos == 0 && bebidas == 0 && postres == 0) {
            return "Sin ventas";
        }
        if (platos >= bebidas && platos >= postres) {
            return "Plato fuerte";
        } else if (bebidas >= platos && bebidas >= postres) {
            return "Bebida";
        } else {
            return "Postre";
        }
    }

    // ---------- Getters ----------
    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Producto> getMenu() {
        return menu;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}