import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    private static Scanner sc = new Scanner(System.in);
    private static Restaurante restaurante =
            new Restaurante("Sabor & Tradición", "900.123.456-7", "Armenia, Quindío");

    public static void main(String[] args) {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println();
            System.out.println("=====================================================");
            System.out.println("  " + restaurante.getNombreComercial()
                    + " | NIT: " + restaurante.getNit());
            System.out.println("  " + restaurante.getDireccion());
            System.out.println("=====================================================");
            System.out.println(" 1. Registrar producto en el menú");
            System.out.println(" 2. Registrar cliente");
            System.out.println(" 3. Consultar productos disponibles");
            System.out.println(" 4. Consultar productos rechazados (calidad)");
            System.out.println(" 5. Crear pedido");
            System.out.println(" 6. Agregar producto a un pedido");
            System.out.println(" 7. Solicitar domicilio para un pedido");
            System.out.println(" 8. Confirmar pedido");
            System.out.println(" 9. Calcular valor total de un pedido");
            System.out.println("10. Consultar pedidos de un cliente");
            System.out.println("11. Actualizar estado de un pedido");
            System.out.println("12. Consultar estado de un pedido");
            System.out.println("13. Reportes de ventas");
            System.out.println("14. Cargar datos de ejemplo");
            System.out.println(" 0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    consultarDisponibles();
                    break;
                case 4:
                    consultarRechazados();
                    break;
                case 5:
                    crearPedido();
                    break;
                case 6:
                    agregarProductoAPedido();
                    break;
                case 7:
                    solicitarDomicilio();
                    break;
                case 8:
                    confirmarPedido();
                    break;
                case 9:
                    calcularTotalPedido();
                    break;
                case 10:
                    consultarPedidosCliente();
                    break;
                case 11:
                    actualizarEstadoPedido();
                    break;
                case 12:
                    consultarEstadoPedido();
                    break;
                case 13:
                    mostrarReportes();
                    break;
                case 14:
                    cargarDatosEjemplo();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar el sistema! Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    // ------------------------------------------------------------------
    // Métodos para leer datos del teclado
    // ------------------------------------------------------------------
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("Debe ingresar un número entero.");
            System.out.print(mensaje);
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.println("Debe ingresar un número.");
            System.out.print(mensaje);
        }
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }

    private static boolean leerSiNo(String mensaje) {
        String texto = leerTexto(mensaje + " (s/n): ");
        while (!texto.equalsIgnoreCase("s") && !texto.equalsIgnoreCase("n")) {
            System.out.println("Responda con 's' o 'n'.");
            texto = leerTexto(mensaje + " (s/n): ");
        }
        return texto.equalsIgnoreCase("s");
    }

    private static String dinero(double valor) {
        return "$" + Math.round(valor);
    }

    // ------------------------------------------------------------------
    // 1. Registrar producto
    // ------------------------------------------------------------------
    private static void registrarProducto() {
        System.out.println("\n--- REGISTRAR PRODUCTO ---");
        System.out.println("1. Plato fuerte");
        System.out.println("2. Bebida");
        System.out.println("3. Postre");
        int tipo = leerEntero("Tipo de producto: ");

        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de producto no válido.");
            return;
        }

        String codigo = leerTexto("Código único: ");
        if (restaurante.buscarIndiceProducto(codigo) != -1) {
            System.out.println("Ya existe un producto con ese código. No se registró.");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripción: ");
        double precioBase = leerDouble("Precio base: ");
        double tiempo = leerDouble("Tiempo de preparación (min): ");

        boolean registrado = false;

        switch (tipo) {
            case 1:
                double pesoPlato = leerDouble("Peso (g): ");
                System.out.println("Tipo de preparación:");
                System.out.println("1. Asado  2. Frito  3. Horneado  4. A la plancha");
                int prep = leerEntero("Opción: ");
                TipoPreparacionPlato tipoPrep;
                switch (prep) {
                    case 1:
                        tipoPrep = TipoPreparacionPlato.ASADO;
                        break;
                    case 2:
                        tipoPrep = TipoPreparacionPlato.FRITO;
                        break;
                    case 3:
                        tipoPrep = TipoPreparacionPlato.HORNEADO;
                        break;
                    default:
                        tipoPrep = TipoPreparacionPlato.A_LA_PLANCHA;
                        break;
                }
                boolean coccion = leerSiNo("¿Superó el control de cocción?");
                registrado = restaurante.registrarProducto(new PlatoFuerte(codigo, nombre,
                        descripcion, precioBase, tiempo, pesoPlato, tipoPrep, coccion));
                break;
            case 2:
                double volumen = leerDouble("Volumen (ml): ");
                int temp = leerEntero("Temperatura de servicio (1. Fría, 2. Caliente): ");
                TipoTemperaturaBebida tipoTemp;
                if (temp == 1) {
                    tipoTemp = TipoTemperaturaBebida.FRIA;
                } else {
                    tipoTemp = TipoTemperaturaBebida.CALIENTE;
                }
                boolean especial = leerSiNo("¿Requiere preparación especial?");
                double tempActual = leerDouble("Temperatura actual (°C): ");
                registrado = restaurante.registrarProducto(new Bebida(codigo, nombre,
                        descripcion, precioBase, tiempo, volumen, tipoTemp, especial, tempActual));
                break;
            case 3:
                double pesoPostre = leerDouble("Peso (g): ");
                double tempAlm = leerDouble("Temperatura de almacenamiento (°C): ");
                boolean refri = leerSiNo("¿Requiere refrigeración?");
                registrado = restaurante.registrarProducto(new Postre(codigo, nombre,
                        descripcion, precioBase, tiempo, pesoPostre, tempAlm, refri));
                break;
            default:
                break;
        }

        if (registrado) {
            System.out.println("Producto registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el producto.");
        }
    }

    // ------------------------------------------------------------------
    // 2. Registrar cliente
    // ------------------------------------------------------------------
    private static void registrarCliente() {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        String documento = leerTexto("Número de documento: ");
        if (restaurante.buscarIndiceCliente(documento) != -1) {
            System.out.println("Ya existe un cliente con ese documento. No se registró.");
            return;
        }
        String nombre = leerTexto("Nombre completo: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo electrónico: ");
        String direccion = leerTexto("Dirección de residencia: ");

        Cliente cliente = new Cliente(documento, nombre, telefono, correo, direccion);
        if (restaurante.registrarCliente(cliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    // ------------------------------------------------------------------
    // 3 y 4. Consultas de productos
    // ------------------------------------------------------------------
    private static void consultarDisponibles() {
        System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
        List<Producto> lista = restaurante.consultarProductosDisponibles();
        if (lista.size() == 0) {
            System.out.println("No hay productos disponibles.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
            System.out.println();
        }
    }

    private static void consultarRechazados() {
        System.out.println("\n--- PRODUCTOS RECHAZADOS POR CALIDAD ---");
        List<Producto> lista = restaurante.consultarProductosRechazados();
        if (lista.size() == 0) {
            System.out.println("No hay productos rechazados.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
            System.out.println();
        }
    }

    // ------------------------------------------------------------------
    // 5. Crear pedido
    // ------------------------------------------------------------------
    private static void crearPedido() {
        System.out.println("\n--- CREAR PEDIDO ---");
        String documento = leerTexto("Documento del cliente: ");
        int posCliente = restaurante.buscarIndiceCliente(documento);
        if (posCliente == -1) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        String codigo = leerTexto("Código único del pedido: ");
        if (restaurante.buscarIndicePedido(codigo) != -1) {
            System.out.println("Ya existe un pedido con ese código.");
            return;
        }
        int mod = leerEntero("Modalidad (1. Consumo en restaurante, 2. Domicilio): ");
        ModalidadPedido modalidad;
        if (mod == 2) {
            modalidad = ModalidadPedido.DOMICILIO;
        } else {
            modalidad = ModalidadPedido.CONSUMO_EN_RESTAURANTE;
        }

        Cliente cliente = restaurante.getClientes().get(posCliente);
        Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, modalidad);
        restaurante.registrarPedido(pedido);
        System.out.println("Pedido creado en estado PENDIENTE. Ahora agréguele productos (opción 6).");
        if (modalidad == ModalidadPedido.DOMICILIO) {
            System.out.println("Recuerde solicitar el domicilio (opción 7).");
        }
    }

    // Pide un código de pedido y devuelve su posición (-1 si no existe)
    private static int pedirPedido() {
        String codigo = leerTexto("Código del pedido: ");
        int pos = restaurante.buscarIndicePedido(codigo);
        if (pos == -1) {
            System.out.println("No existe un pedido con ese código.");
        }
        return pos;
    }

    // ------------------------------------------------------------------
    // 6. Agregar producto a pedido
    // ------------------------------------------------------------------
    private static void agregarProductoAPedido() {
        System.out.println("\n--- AGREGAR PRODUCTOS A UN PEDIDO ---");
        int posPedido = pedirPedido();
        if (posPedido == -1) {
            return;
        }
        Pedido pedido = restaurante.getPedidos().get(posPedido);
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            System.out.println("Solo se pueden agregar productos a pedidos PENDIENTES.");
            return;
        }

        boolean seguir = true;
        while (seguir) {
            String codigoProd = leerTexto("Código del producto: ");
            int posProd = restaurante.buscarIndiceProducto(codigoProd);
            if (posProd == -1) {
                System.out.println("Ese producto no pertenece al menú.");
            } else {
                int cantidad = leerEntero("Cantidad: ");
                Producto producto = restaurante.getMenu().get(posProd);
                pedido.agregarDetalle(producto, cantidad);
                System.out.println("Agregado: " + producto.getNombre() + " x" + cantidad);
                if (cantidad <= 0) {
                    System.out.println("Aviso: con cantidad menor o igual a cero el pedido NO podrá confirmarse.");
                }
            }
            seguir = leerSiNo("¿Desea agregar otro producto?");
        }
    }

    // ------------------------------------------------------------------
    // 7. Solicitar domicilio
    // ------------------------------------------------------------------
    private static void solicitarDomicilio() {
        System.out.println("\n--- SOLICITAR DOMICILIO ---");
        int posPedido = pedirPedido();
        if (posPedido == -1) {
            return;
        }
        Pedido pedido = restaurante.getPedidos().get(posPedido);
        if (pedido.getModalidad() != ModalidadPedido.DOMICILIO) {
            System.out.println("Este pedido es para consumo en el restaurante, no requiere domicilio.");
            return;
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            System.out.println("Solo se puede solicitar domicilio en pedidos PENDIENTES.");
            return;
        }

        System.out.println("1. RápidoExpress (hasta 12 km)");
        System.out.println("2. EntregaSegura (hasta 20 km)");
        int opcion = leerEntero("Seleccione la empresa: ");
        IEmpresaTransporte empresa;
        if (opcion == 1) {
            empresa = new RapidoExpress();
        } else if (opcion == 2) {
            empresa = new EntregaSegura();
        } else {
            System.out.println("Empresa no válida.");
            return;
        }

        double distancia = leerDouble("Distancia del recorrido (km): ");
        pedido.asignarTransporte(empresa, distancia);

        if (pedido.isServicioAceptado()) {
            System.out.println(empresa.getNombreEmpresa() + " ACEPTÓ el servicio. Costo del domicilio: "
                    + dinero(pedido.getCostoDomicilio()));
        } else {
            System.out.println(empresa.getNombreEmpresa()
                    + " NO aceptó el servicio (distancia fuera de cobertura). Pruebe con otra empresa.");
        }
    }

    // ------------------------------------------------------------------
    // 8. Confirmar pedido
    // ------------------------------------------------------------------
    private static void confirmarPedido() {
        System.out.println("\n--- CONFIRMAR PEDIDO ---");
        int posPedido = pedirPedido();
        if (posPedido == -1) {
            return;
        }
        Pedido pedido = restaurante.getPedidos().get(posPedido);

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            System.out.println("El pedido ya está en estado " + pedido.getEstado()
                    + " y no puede confirmarse de nuevo.");
            return;
        }

        if (pedido.confirmarPedido(restaurante.getMenu())) {
            System.out.println("Pedido confirmado correctamente. Total: "
                    + dinero(pedido.calcularValorTotalPedido()));
        } else {
            System.out.println("No se pudo confirmar el pedido.");
            System.out.println("Motivo: " + pedido.obtenerMotivoRechazo(restaurante.getMenu()));
        }
    }

    // ------------------------------------------------------------------
    // 9. Calcular valor total
    // ------------------------------------------------------------------
    private static void calcularTotalPedido() {
        System.out.println("\n--- VALOR TOTAL DE UN PEDIDO ---");
        int posPedido = pedirPedido();
        if (posPedido == -1) {
            return;
        }
        mostrarPedido(restaurante.getPedidos().get(posPedido));
    }

    // Muestra todos los datos de un pedido
    private static void mostrarPedido(Pedido pedido) {
        System.out.println("-----------------------------------------------");
        System.out.println("Pedido: " + pedido.getCodigoUnico() + " | Fecha: " + pedido.getFecha());
        System.out.println("Cliente: " + pedido.getCliente().getNombreCompleto());
        System.out.println("Modalidad: " + pedido.getModalidad() + " | Estado: " + pedido.getEstado());

        List<DetallePedido> detalles = pedido.getDetalles();
        if (detalles.size() == 0) {
            System.out.println("  (Sin productos)");
        }
        for (int i = 0; i < detalles.size(); i++) {
            DetallePedido d = detalles.get(i);
            System.out.println("  - " + d.getProducto().getNombre() + " x" + d.getCantidad()
                    + " | Unitario: " + dinero(d.getProducto().calcularPrecioVenta())
                    + " | Subtotal: " + dinero(d.calcularSubtotal()));
        }

        System.out.println("Valor de productos: " + dinero(pedido.calcularValorProductos()));
        if (pedido.getModalidad() == ModalidadPedido.DOMICILIO) {
            if (pedido.isTransporteAsignado()) {
                System.out.println("Domicilio (" + pedido.getEmpresaTransporte().getNombreEmpresa()
                        + ", " + pedido.getDistanciaDomicilioKm() + " km): "
                        + dinero(pedido.getCostoDomicilio()));
            } else {
                System.out.println("Domicilio: aún no solicitado");
            }
        }
        System.out.println("TOTAL DEL PEDIDO: " + dinero(pedido.calcularValorTotalPedido()));
    }

    // ------------------------------------------------------------------
    // 10. Pedidos de un cliente
    // ------------------------------------------------------------------
    private static void consultarPedidosCliente() {
        System.out.println("\n--- PEDIDOS DE UN CLIENTE ---");
        String documento = leerTexto("Documento del cliente: ");
        int posCliente = restaurante.buscarIndiceCliente(documento);
        if (posCliente == -1) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        Cliente cliente = restaurante.getClientes().get(posCliente);
        List<Pedido> historial = cliente.consultarHistorial();
        System.out.println("Historial de " + cliente.getNombreCompleto() + ":");
        if (historial.size() == 0) {
            System.out.println("El cliente aún no tiene pedidos.");
        }
        for (int i = 0; i < historial.size(); i++) {
            mostrarPedido(historial.get(i));
        }
    }

    // ------------------------------------------------------------------
    // 11. Actualizar estado
    // ------------------------------------------------------------------
    private static void actualizarEstadoPedido() {
        System.out.println("\n--- ACTUALIZAR ESTADO DE UN PEDIDO ---");
        int posPedido = pedirPedido();
        if (posPedido == -1) {
            return;
        }
        Pedido pedido = restaurante.getPedidos().get(posPedido);
        System.out.println("Estado actual: " + pedido.getEstado());
        System.out.println("1. Marcar como ENTREGADO");
        System.out.println("2. Marcar como CANCELADO");
        System.out.println("(Para CONFIRMAR use la opción 8 del menú principal)");
        int opcion = leerEntero("Seleccione: ");

        EstadoPedido estadoAnterior = pedido.getEstado();

        switch (opcion) {
            case 1:
                pedido.actualizarEstado(EstadoPedido.ENTREGADO);
                break;
            case 2:
                pedido.actualizarEstado(EstadoPedido.CANCELADO);
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        if (pedido.getEstado() == estadoAnterior) {
            System.out.println("No se pudo cambiar el estado. Recuerde: solo se entrega un pedido "
                    + "CONFIRMADO, y un pedido cancelado o entregado ya no cambia.");
        } else {
            System.out.println("Estado actualizado a: " + pedido.getEstado());
        }
    }

    // ------------------------------------------------------------------
    // 12. Consultar estado
    // ------------------------------------------------------------------
    private static void consultarEstadoPedido() {
        System.out.println("\n--- ESTADO DE UN PEDIDO ---");
        int posPedido = pedirPedido();
        if (posPedido == -1) {
            return;
        }
        Pedido pedido = restaurante.getPedidos().get(posPedido);
        System.out.println("El pedido " + pedido.getCodigoUnico() + " está en estado: "
                + pedido.getEstado());
    }

    // ------------------------------------------------------------------
    // 13. Reportes
    // ------------------------------------------------------------------
    private static void mostrarReportes() {
        System.out.println("\n--- REPORTES DE VENTAS ---");
        System.out.println("Ingresos del restaurante (sin domicilios): "
                + dinero(restaurante.calcularIngresosRestaurante()));
        System.out.println("Valores cobrados por domicilios (aparte):  "
                + dinero(restaurante.calcularIngresosDomicilios()));

        if (restaurante.hayVentas()) {
            Producto masVendido = restaurante.obtenerProductoMasVendido();
            System.out.println("Producto más vendido: " + masVendido.getNombre() + " ("
                    + restaurante.contarUnidadesVendidas(masVendido.getCodigo()) + " unidades)");
        } else {
            System.out.println("Producto más vendido: aún no hay ventas.");
        }

        System.out.println("Categoría con mayores ingresos: "
                + restaurante.obtenerCategoriaConMayorIngreso());
        System.out.println("   Platos fuertes: " + dinero(restaurante.calcularIngresosPorCategoria("Plato fuerte")));
        System.out.println("   Bebidas:        " + dinero(restaurante.calcularIngresosPorCategoria("Bebida")));
        System.out.println("   Postres:        " + dinero(restaurante.calcularIngresosPorCategoria("Postre")));
    }

    // ------------------------------------------------------------------
    // 14. Datos de ejemplo
    // ------------------------------------------------------------------
    private static void cargarDatosEjemplo() {
        restaurante.registrarProducto(new PlatoFuerte("P01", "Bandeja paisa", "Plato típico",
                30000, 25, 700, TipoPreparacionPlato.ASADO, true));
        restaurante.registrarProducto(new PlatoFuerte("P02", "Trucha frita", "Con patacón",
                25000, 20, 450, TipoPreparacionPlato.FRITO, true));
        // Este plato será rechazado por superar los 900 g
        restaurante.registrarProducto(new PlatoFuerte("P03", "Parrillada gigante", "Para compartir",
                60000, 45, 1200, TipoPreparacionPlato.ASADO, true));
        restaurante.registrarProducto(new Bebida("B01", "Limonada de coco", "Fría y cremosa",
                8000, 5, 400, TipoTemperaturaBebida.FRIA, true, 5));
        restaurante.registrarProducto(new Bebida("B02", "Café de la región", "Caliente",
                4000, 5, 250, TipoTemperaturaBebida.CALIENTE, false, 70));
        restaurante.registrarProducto(new Postre("D01", "Tres leches", "Postre refrigerado",
                9000, 5, 150, 4, true));
        restaurante.registrarProducto(new Postre("D02", "Brownie", "Sin refrigeración",
                6000, 5, 120, 22, false));

        restaurante.registrarCliente(new Cliente("1001", "Ana Gómez", "3001112233",
                "ana@correo.com", "Calle 10 # 5-20"));
        restaurante.registrarCliente(new Cliente("1002", "Luis Pérez", "3104445566",
                "luis@correo.com", "Carrera 14 # 8-15"));

        System.out.println("Datos de ejemplo cargados (7 productos y 2 clientes).");
        System.out.println("Clientes: documentos 1001 y 1002. Productos: P01, P02, P03, B01, B02, D01, D02.");
    }
}