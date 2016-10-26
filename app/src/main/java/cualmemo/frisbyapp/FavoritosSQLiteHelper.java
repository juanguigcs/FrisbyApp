package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Memo on 25/10/2016.
 */
public class FavoritosSQLiteHelper extends SQLiteOpenHelper {
    private String DATA_BASE_NAME="FavoritosBD";
    private int DATA_VERSION=1;

    String sqlCreate = "CREATE TABLE Favoritos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idusuario  INTEGER," +
            "idprod  INTEGER," +
            "idfav INTEGER)";
    public FavoritosSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Favoritos");
        db.execSQL(sqlCreate);
    }

    public boolean insertFav (int idusuario, int idprod,int idfav){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idusuario", idusuario);
        contentValues.put("idprod", idprod);
        contentValues.put("idfav", idfav);
        db.insert("Favoritos", null, contentValues);
        return true;
    }
    public void eliminarFav(int idfav, int idusuario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Favoritos","idusuario=" + idusuario + " AND idfav=" + idfav + "", null);
    }

    public Favoritos buscarFav (int idfav, int idusuario){
        SQLiteDatabase db = this.getWritableDatabase();
        Favoritos tempc= new Favoritos();
        Cursor c = db.rawQuery("select * from Favoritos where idusuario=" + idfav + "", null);
        if (c.moveToFirst()) {
            tempc.setIdusuario(c.getInt(1));
            tempc.setIdprod(c.getInt(2));
            tempc.setIdfav(c.getInt(3));
        }
        else{
            tempc.setIdusuario(0);
            tempc.setIdprod(0);
            tempc.setIdfav(-1);
        }
        return tempc;
    }
    public Boolean buscarFav1 (int idfav, int idusuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        Favoritos tempc = new Favoritos();
        Cursor c = db.rawQuery("select * from Favoritos where idusuario=" + idusuario + " AND idfav=" + idfav + "", null);
        if (c.moveToFirst()) {
            return true;
        }
        else{
          return false;
        }
    }
}
