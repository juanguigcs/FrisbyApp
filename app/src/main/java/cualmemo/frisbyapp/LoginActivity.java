package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button bResgistro, bEntrar;
    EditText eUsario, eContrasena;

    //sqlite
    ContactosSQLiteHelper contactos;
    SQLiteDatabase dbContactos;
    ContentValues dataBD;
    Cursor c;

    //pref compartidas
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_activiy);

        //sqlite
        contactos= new ContactosSQLiteHelper(this, "ContactosBD",null,1);
        dbContactos= contactos.getWritableDatabase();

        //instancia boton editText
        bResgistro = (Button) findViewById(R.id.bRegistro);
        bEntrar = (Button) findViewById(R.id.bEntrar);
        eUsario = (EditText) findViewById(R.id.eUsuario);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        bResgistro.setOnClickListener(this);
        bEntrar.setOnClickListener(this);

        //pref compartidas
        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();

        //se verifica si hay un usario activo si es así se aplican los datos para este(entra directo a main
        if(prefs.getInt("v_ingreso",-1)==-1){
         //   Toast.makeText(this, "primera vez", Toast.LENGTH_SHORT).show();
        }
        else{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usuario", prefs.getString("v_usuario", "u"));
                startActivity(intent);
                finish();
        }
    }
    //listener para dos varios botones
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bRegistro:
                Intent intent = new Intent(this, RegistroActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bEntrar:
                c = dbContactos.rawQuery("select * from Contactos where usuario='" + eUsario.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    if(c.getString(3).equals(eContrasena.getText().toString())){
                        editor.putInt("v_ingreso", 1);
                        editor.putString("v_usuario",c.getString(2) );
                        editor.commit();
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

}
