package cualmemo.frisbyapp;

/**
 * Created by El Memo on 02/10/2016.
 */
public class Productos_combo {
    int idImagen ;
    String nombre, descripcion,precio;

    public Productos_combo(int idImagen, String nombre, String descripcion, String precio) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
