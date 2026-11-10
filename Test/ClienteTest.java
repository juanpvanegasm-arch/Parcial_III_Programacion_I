import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("123", "Ana Gómez", "3001234567",
                "ana@correo.com", "Calle 1 # 2-3");
    }

    @Test
    public void nuevoCliente_noEstaRegistrado() {
        assertFalse(cliente.isRegistrado());
    }

    @Test
    public void setRegistrado_true_marcaAlClienteComoRegistrado() {
        cliente.setRegistrado(true);
        assertTrue(cliente.isRegistrado());
    }

    @Test
    public void nuevoCliente_tieneHistorialVacio() {
        assertEquals(0, cliente.consultarHistorial().size());
    }

    @Test
    public void agregarPedidoAlHistorial_guardaElPedido() {
        Pedido pedido = new Pedido("P1", LocalDate.of(2026, 11, 9), cliente,
                ModalidadPedido.CONSUMO_EN_RESTAURANTE);
        cliente.agregarPedidoAlHistorial(pedido);
        assertEquals(1, cliente.consultarHistorial().size());
        assertSame(pedido, cliente.consultarHistorial().get(0));
    }

    @Test
    public void getters_devuelvenLosDatosDelConstructor() {
        assertEquals("123", cliente.getNumeroDocumento());
        assertEquals("Ana Gómez", cliente.getNombreCompleto());
        assertEquals("3001234567", cliente.getTelefono());
        assertEquals("ana@correo.com", cliente.getCorreoElectronico());
        assertEquals("Calle 1 # 2-3", cliente.getDireccionResidencia());
    }
}