package cualmemo.frisbyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // arreglo para ver en el menú -- lista del menú -- Navigation draw
    private String [] opciones = new  String[]{"Principal","Productos","Mi perfil","Cerrar sesión"};
    ListView list;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //info para las promociones
    private Productos_combo[] datos = new Productos_combo[]{
            new Productos_combo(R.drawable.combo1, "$15.000", "Super combo 1", "Realiza tu pedido"),
            new Productos_combo(R.drawable.combo2, "$20.000", "Super combo 2", "Realiza tu pedido"),
            new Productos_combo(R.drawable.combo3, "$18.000", "Super combo 3", "Realiza tu pedido"),
            new Productos_combo(R.drawable.comboa, "$19.000", "Super apanado", "Realiza tu pedido"),
            new Productos_combo(R.drawable.combon, "$23.000", "Super nuggets", "Realiza tu pedido")};
    ListView list2;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        Bundle extras = getIntent().getExtras();
        final String user = extras.getString("usuario");
        final String correo = extras.getString("correo");
        final String contrasena = extras.getString("contrasena");

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
                     //   Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                    case(1):
                        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
                        intent.putExtra("usuario",user);
                        intent.putExtra("correo",correo);
                        intent.putExtra("contrasena",contrasena);
                        startActivity(intent);
                       // finish();
                       // Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                    case(2):
                        Intent intent2= new Intent(getApplicationContext(),PerfilActivity.class);
                        intent2.putExtra("usuario",user);
                        intent2.putExtra("correo",correo);
                        intent2.putExtra("contrasena",contrasena);
                        startActivity(intent2);
                       // finish();
                       // Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                    case(3):
                        Intent intent3= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent3);
                        editor.clear();
                        editor.commit();
                        finish();
                      //  Toast.makeText(getApplicationContext(),"Opcion cerrar  "+String.valueOf(i), Toast.LENGTH_SHORT).show();
                        break;
                }

                list.setItemChecked(i,true);
                drawerLayout.closeDrawer(list);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.abierto, R.string.cerrado);
        drawerLayout.setDrawerListener(drawerToggle);

        Adapter adaptador = new Adapter(this,datos);
        list2 =(ListView)findViewById(R.id.listview);
        list2.setAdapter(adaptador);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),PromocionActivity.class);
                intent.putExtra("numpromo",String.valueOf(i));
                intent.putExtra("usuario",user);
                intent.putExtra("correo",correo);
                intent.putExtra("contrasena",contrasena);
                startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });
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

    //Adaptador para cambiar el item de los productos del combo
    class Adapter extends ArrayAdapter<Productos_combo> {
        public Adapter(Context context, Productos_combo[] datos) {
            super(context, R.layout.pruducto_promo_item, datos);

        }
        //inflar la clase productos_combo
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item= inflater.inflate(R.layout.pruducto_promo_item, null);

            TextView nombre= (TextView)item.findViewById(R.id.tnombre);
            nombre.setText(datos[position].getNombre());

            TextView precio= (TextView)item.findViewById(R.id.tprecio);
            precio.setText(datos[position].getPrecio());

            TextView descripcion= (TextView)item.findViewById(R.id.tdescripcion);
            descripcion.setText(datos[position].getDescripcion());

            ImageView imagen =(ImageView) item.findViewById(R.id.iImagen);
            imagen.setImageResource(datos[position].getIdImagen());

            return (item);
        }
    }
}
