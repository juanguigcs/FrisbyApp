package cualmemo.frisbyapp;

/**
 * Created by Memo on 24/10/2016.
 */
public class Contactos {
   private int idusuario;
    private String usuario,correo,contrasena;

    public Contactos() {
    }
public Contactos(int idusuario, String usuario, String correo, String contrasena) {
        this.idusuario = idusuario;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
