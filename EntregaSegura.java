public class EntregaSegura implements IEmpresaTransporte {

    public boolean aceptarServicio(double distanciaKm) {
        return distanciaKm > 0 && distanciaKm <= 20;
    }

    public double calcularCostoEnvio(double distanciaKm) {
        double costo = 7000 + (1000 * distanciaKm);
        if (distanciaKm > 5) {
            costo = costo + 3000;
        }
        return costo;
    }

    public String getNombreEmpresa() {
        return "EntregaSegura";
    }
}