import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlatoFuerteTest {

    @Test
    public void calcularPrecioVenta_suma20Porciento() {
        PlatoFuerte p = new PlatoFuerte("PF1", "Bandeja", "Bandeja paisa", 20000, 30,
                500, TipoPreparacionPlato.ASADO, true);
        assertEquals(24000, p.calcularPrecioVenta(), 0.01);
    }

    @Test
    public void verificarCondicionesCalidad_platoValido_retornaTrue() {
        PlatoFuerte p = new PlatoFuerte("PF2", "Churrasco", "Carne a la plancha", 25000, 30,
                500, TipoPreparacionPlato.A_LA_PLANCHA, true);
        assertTrue(p.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_pesoMenorAlMinimo_retornaFalse() {
        PlatoFuerte p = new PlatoFuerte("PF3", "Porción pequeña", "Muy poco peso", 15000, 30,
                100, TipoPreparacionPlato.FRITO, true);
        assertFalse(p.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_tiempoPreparacionMuyCorto_retornaFalse() {
        PlatoFuerte p = new PlatoFuerte("PF4", "Pollo", "Pollo horneado", 18000, 5,
                400, TipoPreparacionPlato.HORNEADO, true);
        assertFalse(p.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_sinControlDeCoccion_retornaFalse() {
        PlatoFuerte p = new PlatoFuerte("PF5", "Costillas", "Costillas asadas", 30000, 40,
                600, TipoPreparacionPlato.ASADO, false);
        assertFalse(p.verificarCondicionesCalidad());
    }
}