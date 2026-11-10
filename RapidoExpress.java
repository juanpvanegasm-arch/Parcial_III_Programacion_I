public class RapidoExpress implements IEmpresaTransporte {

    public boolean aceptarServicio(double distanciaKm) {
        return distanciaKm > 0 && distanciaKm <= 12;
    }

    public double calcularCostoEnvio(double distanciaKm) {
        return 5000 + (1500 * distanciaKm);
    }

    public String getNombreEmpresa() {
        return "RápidoExpress";
    }
}