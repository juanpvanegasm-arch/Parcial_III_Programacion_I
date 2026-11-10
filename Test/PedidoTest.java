import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    private Cliente cliente;
    private PlatoFuerte plato;
    private List<Producto> menu;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("123", "Ana Gómez", "3001234567",
                "ana@correo.com", "Calle 1 # 2-3");
        cliente.setRegistrado(true);
        // precio de venta = 24000, cumple calidad
        plato = new PlatoFuerte("PF1", "Bandeja", "Bandeja paisa", 20000, 30,
                500, TipoPreparacionPlato.ASADO, true);
        menu = new ArrayList<Producto>();
        menu.add(plato);
    }

    @Test
    public void calcularValorTotalPedido_conDomicilio_sumaProductosMasEnvio() {
        Pedido pedido = new Pedido("P1", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.DOMICILIO);
        pedido.agregarDetalle(plato, 2);                      // 48000
        pedido.asignarTransporte(new RapidoExpress(), 4);     // 5000 + 6000 = 11000
        assertEquals(59000, pedido.calcularValorTotalPedido(), 0.01);
    }

    @Test
    public void confirmarPedido_pedidoValido_cambiaEstadoAConfirmado() {
        Pedido pedido = new Pedido("P2", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 1);
        assertTrue(pedido.confirmarPedido(menu));
        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
    }

    @Test
    public void obtenerMotivoRechazo_clienteNoRegistrado_devuelveElMotivo() {
        Cliente noRegistrado = new Cliente("999", "Luis Pérez", "3100000000",
                "luis@correo.com", "Carrera 5 # 6-7");
        Pedido pedido = new Pedido("P3", LocalDate.of(2026, 11, 9), noRegistrado,
                ModalidadPedido.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 1);
        assertEquals("El cliente no está registrado en el restaurante.",
                pedido.obtenerMotivoRechazo(menu));
    }

    @Test
    public void confirmarPedido_domicilioFueraDeCobertura_noSeConfirma() {
        Pedido pedido = new Pedido("P4", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.DOMICILIO);
        pedido.agregarDetalle(plato, 1);
        pedido.asignarTransporte(new RapidoExpress(), 15);    // máximo 12 km
        assertFalse(pedido.isServicioAceptado());
        assertFalse(pedido.confirmarPedido(menu));
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
    }

    @Test
    public void actualizarEstado_entregadoDesdePendiente_noCambiaElEstado() {
        Pedido pedido = new Pedido("P5", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.CONSUMO_EN_RESTAURANTE);
        pedido.agregarDetalle(plato, 1);
        pedido.actualizarEstado(EstadoPedido.ENTREGADO);
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
    }
}