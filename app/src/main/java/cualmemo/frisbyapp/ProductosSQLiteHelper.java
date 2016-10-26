package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Memo on 24/10/2016.
 */
public class ProductosSQLiteHelper extends SQLiteOpenHelper{
    private String DATA_BASE_NAME="ProductosBD";
    private int DATA_VERSION=1;

    String sqlCreate = "CREATE TABLE Productos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idprod  INTEGER," +
            "idImagen  INTEGER," +
            "nombre  TEXT," +
            "descripcion TEXT,"+
            "precio      TEXT)";

    public ProductosSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL(sqlCreate);
    }

    public boolean insertProducto (int idprod, int idImagen,String precio, String nombre, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idprod", idprod);
        contentValues.put("idImagen", idImagen);
        contentValues.put("nombre", nombre);
        contentValues.put("descripcion", descripcion);
        contentValues.put("precio", precio);
        db.insert("Productos", null, contentValues);
        return true;
    }

    public Productos_combo buscarProducto (int idprod){
        SQLiteDatabase db = this.getWritableDatabase();
        Productos_combo tempp= new Productos_combo();
        Cursor c = db.rawQuery("select * from Productos where idprod= " + idprod + "", null);
        if (c.moveToFirst()) {
            tempp.setIdprod(c.getInt(1));
            tempp.setIdImagen(c.getInt(2));
            tempp.setNombre(c.getString(3));
            tempp.setDescripcion(c.getString(4));
            tempp.setPrecio(c.getString(5));
        }
        else{
            tempp.setIdprod(-1);
            tempp.setIdImagen(0);
            tempp.setNombre("");
            tempp.setDescripcion("");
            tempp.setPrecio("");
        }
        return tempp;
    }
}
