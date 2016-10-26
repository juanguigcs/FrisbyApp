package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by El Memo on 28/09/2016.
 */
public class ContactosSQLiteHelper extends SQLiteOpenHelper {
    private String DATA_BASE_NAME="ContactosBD";
    private int DATA_VERSION=1;

    String sqlCreate = "CREATE TABLE Contactos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idusuario  INTEGER," +
            "usuario  TEXT," +
            "contrasena TEXT,"+
            "correo      TEXT)";

    public ContactosSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Contactos");
        db.execSQL(sqlCreate);
    }

    public boolean insertContactos (int idusuario, String usuario, String contrasena, String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idusuario", idusuario);
        contentValues.put("usuario", usuario);
        contentValues.put("contrasena", contrasena);
        contentValues.put("correo", correo);
        db.insert("Contactos", null, contentValues);
        return true;
    }

    public Contactos buscarContactos (String usario){
        SQLiteDatabase db = this.getWritableDatabase();
        Contactos tempc= new Contactos();
        Cursor c = db.rawQuery("select * from Contactos where usuario='" + usario + "'", null);
        if (c.moveToFirst()) {
            tempc.setIdusuario(c.getInt(1));
            tempc.setUsuario(c.getString(2));
            tempc.setContrasena(c.getString(3));
            tempc.setCorreo(c.getString(4));
        }
        else{
            tempc.setIdusuario(-1);
            tempc.setUsuario("");
            tempc.setContrasena("");
            tempc.setCorreo("");
        }
    return tempc;
    }

}