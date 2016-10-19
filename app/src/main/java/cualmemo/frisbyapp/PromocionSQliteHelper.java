package cualmemo.frisbyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by El Memo on 18/10/2016.
 */
public class PromocionSQliteHelper  extends SQLiteOpenHelper {
    private String DATA_BASE_NAME="PromocionBD";
    private int DATA_VERSION=1;
    public  final String TABLA_PROMOCION = "Promocion";

    public  final String CLI_ID = "id";
    public  final String CLI_IDIMAGEN = "idimagen";
    public  final String CLI_PRECIO= "precio";
    public  final String CLI_NOMBRE= "nombre";
    public  final String CLI_DESCRIPCION= "descripcion";

    public SQLiteDatabase db;
    String sqlCreate = "CREATE TABLE "+TABLA_PROMOCION+" (" +
            CLI_ID +"   INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CLI_IDIMAGEN +" INTEGER," +
            CLI_PRECIO +"  TEXT," +
            CLI_NOMBRE +" TEXT,"+
            CLI_DESCRIPCION +"  TEXT)";

    public PromocionSQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Promocion");
        db.execSQL(sqlCreate);
    }
    public Cursor Cargar_BD(){
        String[] columnas= new String[]{CLI_ID,CLI_IDIMAGEN,CLI_PRECIO,CLI_NOMBRE,CLI_DESCRIPCION};

        return db.query("select * from Promocion",columnas,null,null,null,null,null);
        //return db.query(TABLA_PROMOCION,columnas,null,null,null,null,null);
    }


}
