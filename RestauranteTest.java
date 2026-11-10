import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {

    private Restaurante restaurante;
    private Cliente cliente;
    private PlatoFuerte plato;
    private Bebida bebida;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante("La Buena Mesa", "900123456-1", "Calle 10 # 5-20");
        cliente = new Cliente("123", "Ana Gómez", "3001234567",
                "ana@correo.com", "Calle 1 # 2-3");
        // precio de venta = 24000
        plato = new PlatoFuerte("PF1", "Bandeja", "Bandeja paisa", 20000, 30,
                500, TipoPreparacionPlato.ASADO, true);
        // precio de venta = 5500
        bebida = new Bebida("B1", "Limonada", "Natural", 5000, 5,
                350, TipoTemperaturaBebida.FRIA, false, 5);
    }

    @Test
    public void registrarProducto_codigoDuplicado_noSeRegistra() {
        assertTrue(restaurante.registrarProducto(plato));
        assertFalse(restaurante.registrarProducto(plato));
        assertEquals(1, restaurante.getMenu().size());
    }

    @Test
    public void registrarCliente_marcaComoRegistradoYRechazaDuplicados() {
        assertTrue(restaurante.registrarCliente(cliente));
        assertTrue(cliente.isRegistrado());
        assertFalse(restaurante.registrarCliente(cliente));
        assertEquals(1, restaurante.getClientes().size());
    }

    @Test
    public void consultarProductos_separaDisponiblesYRechazados() {
        PlatoFuerte malo = new PlatoFuerte("PF2", "Plato pequeño", "Poco peso", 10000, 30,
                100, TipoPreparacionPlato.FRITO, true);
        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(malo);

        assertEquals(1, restaurante.consultarProductosDisponibles().size());
        assertEquals(1, restaurante.consultarProductosRechazados().size());
        assertEquals("PF2", restaurante.consultarProductosRechazados().get(0).getCodigo());
    }

    @Test
    public void calcularIngresosRestaurante_soloCuentaPedidosConfirmados() {
        restaurante.registrarProducto(plato);
        restaurante.registrarCliente(cliente);
        Pedido pedido = new Pedido("P1", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 2);                       // 48000
        restaurante.registrarPedido(pedido);

        assertEquals(0, restaurante.calcularIngresosRestaurante(), 0.01);   // pendiente

        pedido.confirmarPedido(restaurante.getMenu());
        assertEquals(48000, restaurante.calcularIngresosRestaurante(), 0.01);
    }

    @Test
    public void obtenerCategoriaConMayorIngreso_devuelveLaCategoriaConMasVentas() {
        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(bebida);
        restaurante.registrarCliente(cliente);
        Pedido pedido = new Pedido("P2", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 2);                       // 48000
        pedido.agregarDetalle(bebida, 1);                      // 5500
        restaurante.registrarPedido(pedido);
        pedido.confirmarPedido(restaurante.getMenu());

        assertEquals("Plato fuerte", restaurante.obtenerCategoriaConMayorIngreso());
    }
}