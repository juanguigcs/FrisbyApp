package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //sqlite
    PromocionSQliteHelper Promocion;
    SQLiteDatabase dbPromocion;
    ContentValues datapBD;

    // arreglo para ver en el menú -- lista del menú -- Navigation draw
    private String [] opciones = new  String[]{"Principal","Productos","Mi perfil","Cerrar sesión"};
    ListView list;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //info para las promociones
    /*private Productos_combo[] datos = new Productos_combo[]{
            new Productos_combo(R.drawable.combo1, "$15.000", "Super combo 1", "Realiza tu pedido"),
            new Productos_combo(R.drawable.combo2, "$20.000", "Super combo 2", "Realiza tu pedido"),
            new Productos_combo(R.drawable.combo3, "$18.000", "Super combo 3", "Realiza tu pedido"),
            new Productos_combo(R.drawable.comboa, "$19.000", "Super apanado", "Realiza tu pedido"),
            new Productos_combo(R.drawable.combon, "$23.000", "Super nuggets", "Realiza tu pedido")};*/

    ListView list2,list3;

    //pref compartidas
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    //ArrayList vamoaver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Productos_combo> vamoaver = new ArrayList<>();
        vamoaver.add(new Productos_combo(R.drawable.combo1, "$15.000", "Super combo 1", "Realiza tu pedido"));
        vamoaver.add(new Productos_combo(R.drawable.combo2, "$20.000", "Super combo 2", "Realiza tu pedido"));
        vamoaver.add(new Productos_combo(R.drawable.combo3, "$18.000", "Super combo 3", "Realiza tu pedido"));
        vamoaver.add(new Productos_combo(R.drawable.comboa, "$19.000", "Super apanado", "Realiza tu pedido"));

        Myadapter  myadapter =new Myadapter(this,vamoaver);
        list2 =(ListView)findViewById(R.id.listview);
        list2.setAdapter(myadapter);

        //sqlite
        Promocion= new PromocionSQliteHelper(this, "PromocionDB",null,1);
        dbPromocion= Promocion.getWritableDatabase();

        //pref compartidas
        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        //llenar primera vez la tabla promocion de productos
        if(prefs.getInt("v_ingreso2",-1)==-1){
            Toast.makeText(this, "primera vez"+prefs.getInt("v_ingreso",-1), Toast.LENGTH_SHORT).show();
            editor.putInt("v_ingreso2", 2);
            editor.commit();
            datapBD = new ContentValues();
            datapBD.put("idimagen", R.drawable.combo1);
            datapBD.put("precio", "$15.000");
            datapBD.put("nombre", "Super combo 1");
            datapBD.put("descripcion", "Realiza tu pedido");
            dbPromocion.insert("Promocion", null, datapBD);

            datapBD = new ContentValues();
            datapBD.put("idimagen", R.drawable.combo2);
            datapBD.put("precio", "$16.000");
            datapBD.put("nombre", "Super combo 1");
            datapBD.put("descripcion", "Realiza tu pedido");
            dbPromocion.insert("Promocion", null, datapBD);

            datapBD = new ContentValues();
            datapBD.put("idimagen", R.drawable.combo3);
            datapBD.put("precio", "$17.000");
            datapBD.put("nombre", "Super combo 3");
            datapBD.put("descripcion", "Realiza tu pedido");
            dbPromocion.insert("Promocion", null, datapBD);

            datapBD = new ContentValues();
            datapBD.put("idimagen", R.drawable.comboa);
            datapBD.put("precio", "$18.000");
            datapBD.put("nombre", "Super combo 4");
            datapBD.put("descripcion", "Realiza tu pedido");
            dbPromocion.insert("Promocion", null, datapBD);
           // dbPromocion.execSQL("INSERT INTO Promocion VALUES(null, '"+R.drawable.combo1+"', $15.000,Super combo 1,Realiza tu pedido )");
            //dbPromocion.execSQL("INSERT INTO Promocion VALUES(null, '"+R.drawable.combo2+"', $16.000,Super combo 2,Realiza tu pedido )");
            //dbPromocion.execSQL("INSERT INTO Promocion VALUES(null, '"+R.drawable.combo3+"', $17.000,Super combo 3,Realiza tu pedido )");
            //dbPromocion.execSQL("INSERT INTO Promocion VALUES(null, '"+R.drawable.comboa+"', $18.000,Super combo 4,Realiza tu pedido )");
            Toast.makeText(this, "segunda vez"+prefs.getInt("v_ingreso2",-1), Toast.LENGTH_SHORT).show();

        }
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
                        break;
                    case(1):
                        Intent intent= new Intent(getApplicationContext(),CatalogoActivity.class);
                        startActivity(intent);
                    case(2):
                        Intent intent2= new Intent(getApplicationContext(),PerfilActivity.class);
                        startActivity(intent2);
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


        //list view para promocion
        /*Adapter adaptador = new Adapter(this,datos);
        list2 =(ListView)findViewById(R.id.listview);
        list2.setAdapter(adaptador);*/

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),PromocionActivity.class);
                intent.putExtra("numpromo",String.valueOf(i));
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Opcion "+String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

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
    //Adaptador para cambiar el item de los productos del combo
    /*class Adapter extends ArrayAdapter<Productos_combo> {
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
    }*/
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
