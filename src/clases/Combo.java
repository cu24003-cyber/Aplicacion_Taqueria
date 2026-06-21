package clases;

import java.util.List;

public class Combo {
    
    private String idCombo;
    private String nombre;
    private double precioCombo;
    private List<Detalle> detalle;

    public Combo() {}

    public String getIdCombo() { return idCombo; }
    public void setIdCombo(String idCombo) { this.idCombo = idCombo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioCombo() { return precioCombo; }
    public void setPrecioCombo(double precioCombo) { this.precioCombo = precioCombo; }

    public List<Detalle> getDetalle() { return detalle; }
    public void setDetalle(List<Detalle> detalle) { this.detalle = detalle; }
}
