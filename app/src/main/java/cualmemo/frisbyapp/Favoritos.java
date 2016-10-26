package cualmemo.frisbyapp;

/**
 * Created by Memo on 25/10/2016.
 */
public class Favoritos {
    int idusuario,  idprod, idfav;

    public Favoritos(int idusuario, int idprod, int idfav) {
        this.idusuario = idusuario;
        this.idprod = idprod;
        this.idfav = idfav;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdprod() {
        return idprod;
    }

    public void setIdprod(int idprod) {
        this.idprod = idprod;
    }

    public int getIdfav() {
        return idfav;
    }

    public void setIdfav(int idfav) {
        this.idfav = idfav;
    }

    public Favoritos() {
    }
}
