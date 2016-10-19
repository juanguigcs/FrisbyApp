package cualmemo.frisbyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

public class PromocionActivity extends AppCompatActivity {

    // arreglo para ver en el menú -- lista del menú -- Navigation draw
    private String [] opciones = new  String[]{"Principal","Productos","Mi perfil","Cerrar sesión"};
    ListView list;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocion);

        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        Bundle extras = getIntent().getExtras();
        String numpromo = extras.getString("numpromo");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        //Toast.makeText(getApplicationContext(),"la opcion desde la actividad es "+numpromo, Toast.LENGTH_SHORT).show();
        Fragment fragment = null;
        switch (Integer.parseInt(numpromo)) {
            case (0):
                fragment = new p1Fragment();
                ft.replace(R.id.contenedorFrame, fragment).commit();
                break;
            case (1):
                fragment = new p2Fragment();
                ft.replace(R.id.contenedorFrame, fragment).commit();
                break;
            case (2):
                fragment = new p3Fragment();
                ft.replace(R.id.contenedorFrame, fragment).commit();
                break;
            case (3):
                fragment = new p4Fragment();
                ft.replace(R.id.contenedorFrame, fragment).commit();
                break;
            case (4):
                fragment = new p5Fragment();
                ft.replace(R.id.contenedorFrame, fragment).commit();
                break;

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
                Fragment fragment = null;
                switch (i){
                    case(0):
                        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                    case(1):
                        Intent intent2= new Intent(getApplicationContext(),CatalogoActivity.class);
                        startActivity(intent2);
                        break;
                    case(2):
                        Intent intent3= new Intent(getApplicationContext(),PerfilActivity.class);
                        startActivity(intent3);
                        break;
                    case(3):
                        Intent intent4= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent4);
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
