public abstract class Producto implements IElementoMenu {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private double tiempoPreparacionMin;

    public Producto(String codigo, String nombre, String descripcion,
                    double precioBase, double tiempoPreparacionMin) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    public abstract double calcularPrecioVenta();

    public abstract boolean verificarCondicionesCalidad();

    // Método auxiliar para identificar la categoría en los reportes
    public abstract String getCategoria();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getTiempoPreparacionMin() {
        return tiempoPreparacionMin;
    }

    public void setTiempoPreparacionMin(double tiempoPreparacionMin) {
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    public String obtenerInformacion() {
        String calidad;
        if (verificarCondicionesCalidad()) {
            calidad = "Cumple";
        } else {
            calidad = "NO cumple";
        }
        return "[" + codigo + "] " + nombre + " (" + getCategoria() + ")"
                + "\n   Descripción: " + descripcion
                + "\n   Precio base: $" + Math.round(precioBase)
                + " | Precio de venta: $" + Math.round(calcularPrecioVenta())
                + "\n   Tiempo de preparación: " + tiempoPreparacionMin + " min"
                + " | Calidad: " + calidad;
    }
}