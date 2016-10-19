package cualmemo.frisbyapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegistroActivity extends AppCompatActivity {

    Button brAceptar,brCancelar;
    EditText erUsario, erContrasena,erRContrasena,erCorreo;

   //sqlite
    ContactosSQLiteHelper contactos;
    SQLiteDatabase dbContactos;
    ContentValues dataBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro);

        //sqlite
        contactos= new ContactosSQLiteHelper(this, "ContactosBD",null,1);
        dbContactos= contactos.getWritableDatabase();

        //instancia boton editText
        erUsario=(EditText)findViewById(R.id.erUsuario);
        erContrasena=(EditText)findViewById(R.id.erContrasena);
        erRContrasena=(EditText)findViewById(R.id.erRContrasena);
        erCorreo=(EditText)findViewById(R.id.erCorreo);
        brAceptar=(Button)findViewById(R.id.brAceptar);
        brCancelar=(Button)findViewById(R.id.brCancelar);

        //forma individual del listener
        brAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (valida()) {
                    //Toast.makeText(getApplicationContext(), "va entrar.-. ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        brCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //función para validar campos vacíos, contraseña y existencia
   protected boolean valida () {
        String temp_usuario=erUsario.getText().toString();
        String temp_contrasena=erContrasena.getText().toString();
        String temp_rcontrasena=erRContrasena.getText().toString();
        String temp_correo=erCorreo.getText().toString();

       if(TextUtils.isEmpty(temp_usuario)||TextUtils.isEmpty(temp_contrasena)||TextUtils.isEmpty(temp_rcontrasena)||TextUtils.isEmpty(temp_correo)){
               Toast.makeText(this, "Campos vacíos .-. ",Toast.LENGTH_SHORT).show();
               return false;

       }
       else{
           if(temp_contrasena.equals(temp_rcontrasena)) {
                Cursor c = dbContactos.rawQuery("select * from Contactos where usuario='" + temp_usuario + "'", null);
                if (c.moveToFirst()) {
                    Toast.makeText(this, "El usario ya existe.-. ", Toast.LENGTH_SHORT).show();
                    erUsario.setText("");
                    erCorreo.setText("");
                    erContrasena.setText("");
                    erRContrasena.setText("");
                    return false;
                }
                else {
                    dataBD = new ContentValues();
                    dataBD.put("usuario", temp_usuario);
                    dataBD.put("contrasena", temp_contrasena);
                    dataBD.put("rcontrasena", temp_rcontrasena);
                    dataBD.put("correo", temp_correo);

                    dbContactos.insert("Contactos", null, dataBD);
                    return true;
                }
           }
           else{
                Toast.makeText(this, "La contraseña no coincide .-. ",Toast.LENGTH_SHORT).show();
                erContrasena.setText("");
                erRContrasena.setText("");
                return false;
                }
       }
   }
}
