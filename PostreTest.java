import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PostreTest {

    @Test
    public void calcularPrecioVenta_sinRefrigeracion_suma15Porciento() {
        Postre p = new Postre("PO1", "Brownie", "Brownie de chocolate", 10000, 10,
                150, 22, false);
        assertEquals(11500, p.calcularPrecioVenta(), 0.01);
    }

    @Test
    public void calcularPrecioVenta_conRefrigeracion_suma15MasRecargoDe5Porciento() {
        Postre p = new Postre("PO2", "Cheesecake", "Cheesecake de fresa", 10000, 10,
                150, 4, true);
        assertEquals(12000, p.calcularPrecioVenta(), 0.01);
    }

    @Test
    public void verificarCondicionesCalidad_postreRefrigeradoValido_retornaTrue() {
        Postre p = new Postre("PO3", "Flan", "Flan de caramelo", 8000, 10,
                200, 4, true);
        assertTrue(p.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_refrigeradoConTemperaturaAlta_retornaFalse() {
        Postre p = new Postre("PO4", "Tiramisú", "Tiramisú clásico", 12000, 10,
                200, 10, true);
        assertFalse(p.verificarCondicionesCalidad());
    }

    @Test
    public void verificarCondicionesCalidad_pesoMenorAlMinimo_retornaFalse() {
        Postre p = new Postre("PO5", "Galleta", "Galleta pequeña", 3000, 5,
                50, 22, false);
        assertFalse(p.verificarCondicionesCalidad());
    }
}