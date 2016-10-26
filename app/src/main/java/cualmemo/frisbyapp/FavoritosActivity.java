package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {
    //sqlite
    ProductosSQLiteHelper productosSQLiteHelper;
    SQLiteDatabase dbProductos;
    ContentValues datapBD;
    FavoritosSQLiteHelper favoritosSQLiteHelper;
    SQLiteDatabase dbFavoritos;
    ContactosSQLiteHelper contactos;
    SQLiteDatabase dbContactos;

    // arreglo para ver en el menú -- lista del menú -- Navigation draw
    private String [] opciones = new  String[]{"Principal","Productos","Mi perfil","Cerrar sesión"};
    ListView list;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    ListView list2;

    //pref compartidas
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        //pref compartidas
        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        //sqlite
        productosSQLiteHelper= new ProductosSQLiteHelper(this, "ProductosBD",null,1);
        dbProductos= productosSQLiteHelper.getWritableDatabase();
        favoritosSQLiteHelper= new FavoritosSQLiteHelper(this, "FavoritosBD",null,1);
        dbFavoritos= favoritosSQLiteHelper.getWritableDatabase();
        contactos= new ContactosSQLiteHelper(this, "ContactosBD",null,1);
        dbContactos= contactos.getWritableDatabase();
        //LisVIew
        final ArrayList<Productos_combo> vamoaver = new ArrayList<Productos_combo>();
        for(int i=1;i <=5;i++) {
            if (favoritosSQLiteHelper.buscarFav1(i, contactos.buscarContactos(prefs.getString("v_usuario", "u")).getIdusuario())) {
                vamoaver.add(new Productos_combo(productosSQLiteHelper.buscarProducto(i).getIdprod(), productosSQLiteHelper.buscarProducto(i).getIdImagen(), productosSQLiteHelper.buscarProducto(i).getPrecio(), productosSQLiteHelper.buscarProducto(i).getNombre(), productosSQLiteHelper.buscarProducto(i).getDescripcion()));
            }
        }

        final Myadapter  myadapter =new Myadapter(this,vamoaver);
        list2 =(ListView)findViewById(R.id.listview);
        list2.setAdapter(myadapter);

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent= new Intent(getApplicationContext(),EliminarFavoritosActivity.class);

                //intent.putExtra("numpromo",String.valueOf(i));
                intent.putExtra("numpromo",String.valueOf(vamoaver.get(i).getIdprod()));
                startActivity(intent);
                finish();
                //Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });

        //menu completo
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
                        Intent intent4= new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent4);
                        //finish();
                        break;
                    case(1):
                        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
                        startActivity(intent);
                        //finish();
                        break;
                    case(2):
                        Intent intent2= new Intent(getApplicationContext(),PerfilActivity.class);
                        startActivity(intent2);
                        //finish();
                        break;
                    case(3):
                        Intent intent3= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent3);
                        //editor.clear();
                        editor.remove("v_ingreso");
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
    //Adaptador para cambiar el item de los productos del combo
    public class Myadapter extends ArrayAdapter<Productos_combo>{
        private final Context context;
        private final ArrayList<Productos_combo>  datos ;

        public Myadapter(Context context, ArrayList<Productos_combo>  datos) {
            super(context, -1, datos);
            this.context = context;
            this.datos = datos;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item = inflater.inflate(R.layout.pruducto_promo_item,parent,false);

            TextView nombre= (TextView)item.findViewById(R.id.tnombre);
            TextView precio= (TextView)item.findViewById(R.id.tprecio);
            TextView descripcion= (TextView)item.findViewById(R.id.tdescripcion);
            ImageView imagen =(ImageView) item.findViewById(R.id.iImagen);

            Productos_combo pactual =  datos.get(position);

            nombre.setText( pactual.getNombre());
            precio.setText(pactual.getPrecio());
            descripcion.setText(pactual.getDescripcion());
            imagen.setImageResource(pactual.getIdImagen());

            return (item);
        }
    }
    //menu izq pemzar fuera del cel
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