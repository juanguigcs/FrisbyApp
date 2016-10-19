package cualmemo.frisbyapp;

import android.content.Context;
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
            "usuario     TEXT," +
            "contrasena  TEXT," +
            "rcontrasena TEXT,"+
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
}