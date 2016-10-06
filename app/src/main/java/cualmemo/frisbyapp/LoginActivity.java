package cualmemo.frisbyapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
    //base de datos en variables
    String Array_usuario[] = new String[100];
    String Array_contrasena[] = new String[100];
    String Array_correo[] = new String[100];
    int cont_usuario=0;
    String usuario_in;
    Button bResgistro, bEntrar;
    EditText eUsario, eContrasena;

    //pref compartidas
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int value=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_activiy);

        bResgistro = (Button) findViewById(R.id.bRegistro);
        bEntrar = (Button) findViewById(R.id.bEntrar);
        eUsario = (EditText) findViewById(R.id.eUsuario);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        bResgistro.setOnClickListener(this);
        bEntrar.setOnClickListener(this);

        prefs= getSharedPreferences("uno",MODE_PRIVATE);
        editor=prefs.edit();
        if(prefs.getInt("v_ingreso",-1)==-1){
         //   Toast.makeText(this, "primera vez", Toast.LENGTH_SHORT).show();
        }
        else{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usuario", prefs.getString("v_usuario", "u"));
                intent.putExtra("correo", prefs.getString("v_correo", "c"));
                intent.putExtra("contrasena", prefs.getString("v_contrasena", "p"));
                startActivity(intent);
                finish();
            //}
        }
    }
    //limpiar preferencias
    public void ClearPrefs(View view){
        editor.clear();
        editor.commit();
    }
    //listener para dos varios botones
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bRegistro:
                Intent intent = new Intent(this, RegistroActivity.class);
                startActivityForResult(intent, 1234);
                break;
            case R.id.bEntrar:
                if(prefs.getString("v_usuario", "u").equals(eUsario.getText().toString())){
                    Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                    intent3.putExtra("usuario", prefs.getString("v_usuario", "u"));
                    intent3.putExtra("correo", prefs.getString("v_correo", "c"));
                    intent3.putExtra("contrasena", prefs.getString("v_contrasena", "p"));
                    startActivity(intent3);
                    finish();
                    editor.putInt("v_ingreso", 1);
                    editor.commit();
                }
                else {
                    if (valida()) {
                        editor.putInt("v_ingreso", 1);
                        editor.putString("v_usuario", Array_usuario[Integer.parseInt(usuario_in)]);
                        editor.putString("v_correo", Array_correo[Integer.parseInt(usuario_in)]);
                        editor.putString("v_contrasena", Array_contrasena[Integer.parseInt(usuario_in)]);
                        editor.commit();

                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("usuario", Array_usuario[Integer.parseInt(usuario_in)]);
                        intent2.putExtra("correo", Array_correo[Integer.parseInt(usuario_in)]);
                        intent2.putExtra("contrasena", Array_contrasena[Integer.parseInt(usuario_in)]);
                        intent2.putExtra("usarioint", usuario_in);
                        startActivity(intent2);
                        finish();
                    } else {
                        Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    //funcion propia para validar la info desde el registro
    protected boolean valida() {
        String temp_usuario = eUsario.getText().toString();
        String temp_contrasena = eContrasena.getText().toString();
        boolean flag_valida=false;

        if (TextUtils.isEmpty(temp_usuario) || TextUtils.isEmpty(temp_contrasena)) {
           // Toast.makeText(this, "Campos vacíos .-. ", Toast.LENGTH_SHORT).show();
            flag_valida = false;
        }
        else {
            for (int i = 0; i<= cont_usuario; i++) {
                if (temp_contrasena.equals(Array_contrasena[i]) && temp_usuario.equals(Array_usuario[i])) {
                    flag_valida =true;
                    usuario_in=Integer.toString(i);
                    break;
                }
                else {
                    flag_valida = false;
                    }
                }
            }
        if(flag_valida){
            return true;
        }
        else{
           return false;
        }
    }
    //funcion para abrir una nueva actividad esperando recibir unos datos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1234 && resultCode == RESULT_OK){
            String user = data.getExtras().getString("usuario");
            String contrasena = data.getExtras().getString("contrasena");
            String correo= data.getExtras().getString("correo");
            //lleno mi "base de datos"
            Array_usuario[cont_usuario]= user;
            Array_contrasena[cont_usuario]=contrasena;
            Array_correo[cont_usuario]=correo;
            cont_usuario ++;
        }
        if (requestCode==1234 && resultCode == RESULT_CANCELED){
            Log.d("mensaje","no se cargaron datos");
        }
    }
}
