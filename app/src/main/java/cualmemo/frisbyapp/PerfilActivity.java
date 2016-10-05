package cualmemo.frisbyapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        Bundle extras = getIntent().getExtras();
        final String user = extras.getString("usuario");
        final String correo = extras.getString("correo");
        final String contrasena = extras.getString("contrasena");

        tVusuario =(TextView)findViewById(R.id.tvusuario);
        tVcorreo =(TextView)findViewById(R.id.tvcorreo);
        tVcontrasena =(TextView)findViewById(R.id.tvcontrasena);
        tVusuario.setText(user);
        tVcorreo.setText(correo);
        tVcontrasena.setText(contrasena);

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
                Fragment fragment = null;
                switch (i){
                    case(0):
                        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("usuario",user);
                        intent.putExtra("correo",correo);
                        intent.putExtra("contrasena",contrasena);
                        startActivity(intent);
                        finish();
                     //   Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                    case(1):
                        Intent intent2= new Intent(getApplicationContext(),CatalogoActivity.class);
                        intent2.putExtra("usuario",user);
                        intent2.putExtra("correo",correo);
                        intent2.putExtra("contrasena",contrasena);
                        startActivity(intent2);
                        finish();
                    //    Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
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
                   //     Toast.makeText(getApplicationContext(),"Opcion Cerrar "+String.valueOf(i), Toast.LENGTH_SHORT).show();
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
