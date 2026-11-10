public class PlatoFuerte extends Producto {
    private double pesoGramos;
    private TipoPreparacionPlato tipoPreparacion;
    private boolean controlCoccionAprobado;

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, double tiempoPreparacionMin, double pesoGramos, TipoPreparacionPlato tipoPreparacion, boolean controlCoccionAprobado) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin);
        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.controlCoccionAprobado = controlCoccionAprobado;
    }

    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }

    public boolean verificarCondicionesCalidad() {
        boolean pesoValido = pesoGramos >= 200 && pesoGramos <= 900;
        boolean tiempoValido = getTiempoPreparacionMin() >= 10 && getTiempoPreparacionMin() <= 60;
        return pesoValido && tiempoValido && controlCoccionAprobado;
    }

    public String getCategoria() {
        return "Plato fuerte";
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public TipoPreparacionPlato getTipoPreparacion() {
        return tipoPreparacion;
    }

    public boolean isControlCoccionAprobado() {
        return controlCoccionAprobado;
    }

    public String obtenerInformacion() {
        String cocc;
        if (controlCoccionAprobado) {
            cocc = "aprobada";
        } else {
            cocc = "NO aprobada";
        }
        return super.obtenerInformacion()
                + "\n   Peso: " + pesoGramos + " g | Preparación: " + tipoPreparacion
                + " | Cocción " + cocc;
    }
}
