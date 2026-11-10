import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntregaSeguraTest {

    private EntregaSegura empresa = new EntregaSegura();

    @Test
    public void aceptarServicio_enElLimiteDe20Km_retornaTrue() {
        assertTrue(empresa.aceptarServicio(20));
    }

    @Test
    public void aceptarServicio_masDe20Km_retornaFalse() {
        assertFalse(empresa.aceptarServicio(20.5));
    }

    @Test
    public void calcularCostoEnvio_3Km_noTieneRecargo() {
        // 7000 + 1000 * 3 = 10000
        assertEquals(10000, empresa.calcularCostoEnvio(3), 0.01);
    }

    @Test
    public void calcularCostoEnvio_exactamente5Km_noTieneRecargo() {
        // 7000 + 1000 * 5 = 12000
        assertEquals(12000, empresa.calcularCostoEnvio(5), 0.01);
    }

    @Test
    public void calcularCostoEnvio_10Km_incluyeRecargoDe3000() {
        // 7000 + 1000 * 10 + 3000 = 20000
        assertEquals(20000, empresa.calcularCostoEnvio(10), 0.01);
    }
}