import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RapidoExpressTest {

    private RapidoExpress empresa = new RapidoExpress();

    @Test
    public void aceptarServicio_enElLimiteDe12Km_retornaTrue() {
        assertTrue(empresa.aceptarServicio(12));
    }

    @Test
    public void aceptarServicio_masDe12Km_retornaFalse() {
        assertFalse(empresa.aceptarServicio(12.1));
    }

    @Test
    public void aceptarServicio_distanciaCero_retornaFalse() {
        assertFalse(empresa.aceptarServicio(0));
    }

    @Test
    public void calcularCostoEnvio_4Km_esBaseMasKilometros() {
        // 5000 + 1500 * 4 = 11000
        assertEquals(11000, empresa.calcularCostoEnvio(4), 0.01);
    }

    @Test
    public void getNombreEmpresa_retornaElNombreCorrecto() {
        assertEquals("RápidoExpress", empresa.getNombreEmpresa());
    }
}