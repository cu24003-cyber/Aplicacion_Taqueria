package clases;

public class Categoria {

    private String idCategoria;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(String idCategoria, String descripcion) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   

    @Override
    public String toString() {
        return descripcion;
    }
}
