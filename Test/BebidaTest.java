import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BebidaTest {

    @Test
    public void calcularPrecioVenta_sinPreparacionEspecial_suma10Porciento() {
        Bebida b = new Bebida("B1", "Limonada", "Limonada natural", 10000, 5,
                350, TipoTemperaturaBebida.FRIA, false, 5);
        assertEquals(11000, b.calcularPrecioVenta(), 0.01);
    }

    @Test
    public void calcularPrecioVenta_conPreparacionEspecial_suma3000Adicionales() {
        Bebida b = new Bebida("B2", "Café especial", "Café de origen", 10000, 8,
                300, TipoTemperaturaBebida.CALIENTE, true, 70);
        assertEquals(14000, b.calcularPrecioVenta(), 0.01);
    }

    @Test
    public void verificarCondicionesCalidad_bebidaFriaValida_retornaTrue() {
        Bebida b = new Bebida("B3", "Jugo", "Jugo de mora", 6000, 5,
                350, TipoTemperaturaBebida.FRIA, false, 5);
        assertTrue(b.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_bebidaCalienteMuyFria_retornaFalse() {
        Bebida b = new Bebida("B4", "Chocolate", "Chocolate caliente", 7000, 6,
                300, TipoTemperaturaBebida.CALIENTE, false, 40);
        assertFalse(b.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_volumenMayorAlPermitido_retornaFalse() {
        Bebida b = new Bebida("B5", "Jarra", "Jarra grande", 15000, 5,
                2000, TipoTemperaturaBebida.FRIA, false, 5);
        assertFalse(b.verificarCondicionesCalidad());
    }
}
