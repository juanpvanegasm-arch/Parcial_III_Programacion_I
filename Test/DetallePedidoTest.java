import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetallePedidoTest {

    private PlatoFuerte crearPlato() {
        // precio de venta = 20000 * 1.20 = 24000
        return new PlatoFuerte("PF1", "Bandeja", "Bandeja paisa", 20000, 30,
                500, TipoPreparacionPlato.ASADO, true);
    }

    @Test
    public void calcularSubtotal_cantidadDos_multiplicaPrecioVentaPorCantidad() {
        DetallePedido d = new DetallePedido(crearPlato(), 2);
        assertEquals(48000, d.calcularSubtotal(), 0.01);
    }

    @Test
    public void calcularSubtotal_cantidadUno_esIgualAlPrecioVenta() {
        DetallePedido d = new DetallePedido(crearPlato(), 1);
        assertEquals(24000, d.calcularSubtotal(), 0.01);
    }

    @Test
    public void calcularSubtotal_cantidadCero_retornaCero() {
        DetallePedido d = new DetallePedido(crearPlato(), 0);
        assertEquals(0, d.calcularSubtotal(), 0.01);
    }

    @Test
    public void calcularSubtotal_conBebida_usaPrecioVentaDeLaBebida() {
        // precio de venta = 5000 * 1.10 = 5500
        Bebida b = new Bebida("B1", "Limonada", "Natural", 5000, 5,
                350, TipoTemperaturaBebida.FRIA, false, 5);
        DetallePedido d = new DetallePedido(b, 3);
        assertEquals(16500, d.calcularSubtotal(), 0.01);
    }

    @Test
    public void getters_devuelvenProductoYCantidadRecibidos() {
        PlatoFuerte plato = crearPlato();
        DetallePedido d = new DetallePedido(plato, 4);
        assertSame(plato, d.getProducto());
        assertEquals(4, d.getCantidad());
    }
}