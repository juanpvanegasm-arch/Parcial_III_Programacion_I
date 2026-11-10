public interface IEmpresaTransporte {
    boolean aceptarServicio(double distanciaKm);

    double calcularCostoEnvio(double distanciaKm);

    String getNombreEmpresa();
}