public class Bebida extends Producto {
    private double volumenMl;
    private TipoTemperaturaBebida temperaturaServicio;
    private boolean requierePreparacionEspecial;
    private double temperaturaActual;

    public Bebida(String codigo, String nombre, String descripcion,
                  double precioBase, double tiempoPreparacionMin,
                  double volumenMl, TipoTemperaturaBebida temperaturaServicio,
                  boolean requierePreparacionEspecial, double temperaturaActual) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin);
        this.volumenMl = volumenMl;
        this.temperaturaServicio = temperaturaServicio;
        this.requierePreparacionEspecial = requierePreparacionEspecial;
        this.temperaturaActual = temperaturaActual;
    }

    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.10;
        if (requierePreparacionEspecial) {
            precio = precio + 3000;
        }
        return precio;
    }

    public boolean verificarCondicionesCalidad() {
        boolean volumenValido = volumenMl >= 150 && volumenMl <= 1500;
        boolean temperaturaValida;
        if (temperaturaServicio == TipoTemperaturaBebida.FRIA) {
            temperaturaValida = temperaturaActual >= 0 && temperaturaActual <= 10;
        } else {
            temperaturaValida = temperaturaActual >= 55 && temperaturaActual <= 85;
        }
        return volumenValido && temperaturaValida;
    }

    public String getCategoria() {
        return "Bebida";
    }

    public double getVolumenMl() {
        return volumenMl;
    }

    public TipoTemperaturaBebida getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public boolean isRequierePreparacionEspecial() {
        return requierePreparacionEspecial;
    }

    public double getTemperaturaActual() {
        return temperaturaActual;
    }

    public String obtenerInformacion() {
        String especial;
        if (requierePreparacionEspecial) {
            especial = "sí";
        } else {
            especial = "no";
        }
        return super.obtenerInformacion()
                + "\n   Volumen: " + volumenMl + " ml | Servicio: " + temperaturaServicio
                + " | Temp. actual: " + temperaturaActual + " °C"
                + " | Preparación especial: " + especial;
    }
}