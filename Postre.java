public class Postre extends Producto {
    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    public Postre(String codigo, String nombre, String descripcion,
                  double precioBase, double tiempoPreparacionMin,
                  double pesoGramos, double temperaturaAlmacenamiento,
                  boolean requiereRefrigeracion) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacionMin);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.15;
        if (requiereRefrigeracion) {
            precio = precio + getPrecioBase() * 0.05;
        }
        return precio;
    }

    public boolean verificarCondicionesCalidad() {
        boolean pesoValido = pesoGramos >= 80 && pesoGramos <= 500;
        boolean temperaturaValida;
        if (requiereRefrigeracion) {
            temperaturaValida = temperaturaAlmacenamiento >= 0 && temperaturaAlmacenamiento <= 6;
        } else {
            temperaturaValida = temperaturaAlmacenamiento >= 15 && temperaturaAlmacenamiento <= 30;
        }
        return pesoValido && temperaturaValida;
    }

    public String getCategoria() {
        return "Postre";
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public String obtenerInformacion() {
        String refri;
        if (requiereRefrigeracion) {
            refri = "sí";
        } else {
            refri = "no";
        }
        return super.obtenerInformacion()
                + "\n   Peso: " + pesoGramos + " g | Temp. almacenamiento: "
                + temperaturaAlmacenamiento + " °C | Refrigeración: " + refri;
    }
}