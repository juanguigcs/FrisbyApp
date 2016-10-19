package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    // arreglo para ver en el menú -- lista del menú -- Navigation draw
    private String [] opciones = new  String[]{"Principal","Productos","Mi perfil","Cerrar sesión"};
    ListView list;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    TextView tVusuario, tVcorreo,tVcontrasena;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    //sqlite
    ContactosSQLiteHelper contactos;
    SQLiteDatabase dbContactos;
    ContentValues dataBD;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //pref compartidas
        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        tVusuario =(TextView)findViewById(R.id.tvusuario);
        tVcorreo =(TextView)findViewById(R.id.tvcorreo);
        tVcontrasena =(TextView)findViewById(R.id.tvcontrasena);
        //tVusuario.setText(user);
        //tVcorreo.setText(correo);
        //tVcontrasena.setText(contrasena);

        //sqlite
        contactos= new ContactosSQLiteHelper(this, "ContactosBD",null,1);
        dbContactos= contactos.getWritableDatabase();

        c = dbContactos.rawQuery("select * from Contactos where usuario='" + prefs.getString("v_usuario", "u") + "'", null);
        if (c.moveToFirst()) {
            tVusuario.setText(c.getString(1));
            tVcorreo.setText(c.getString(4));
            tVcontrasena.setText(c.getString(2));
            Toast.makeText(this, "c(1)"+c.getString(1), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "c(2)"+c.getString(2), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "c(3)"+c.getString(3), Toast.LENGTH_SHORT).show();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout)findViewById(R.id.contenedor);
        list=(ListView)findViewById(R.id.mizq);
        list.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case(0):
                        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case(1):
                        Intent intent2= new Intent(getApplicationContext(),CatalogoActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case(2):
                        Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                    case(3):
                        Intent intent3= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent3);
                        editor.clear();
                        editor.commit();
                        finish();
                        break;
                }
        list.setItemChecked(i,true);
        drawerLayout.closeDrawer(list);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.abierto, R.string.cerrado);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
