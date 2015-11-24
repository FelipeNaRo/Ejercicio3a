package com.example.dam2.ejercicio3a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {

    Button boton1,boton2;
    EditText texto;
    String FILE_NAME;
    TextView mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            // Creamos las variables y elementos que vamos a utilizar.
            FILE_NAME=new String("fichero.txt");    //fichero para guardar datos
            boton1= (Button)findViewById(R.id.boton);//botón de enviar datos
            boton2=(Button)findViewById(R.id.boton2);//botón de leer datos
            texto=(EditText)findViewById(R.id.texto);//texto a añadir al fichero
            mostrar=(TextView)findViewById(R.id.mostrar);// mostrará lo guardado
            //listener para grabar
            boton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    grabarDatos();
                }
            });
            //listener para recuperar
            boton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leerDatos();
                }
            });


    }

    public void leerDatos(){
        // recupera los datos del archivo guardado
        try{
            //creamos el fileinput y datainput stream necesarios para leer.
            FileInputStream fis=openFileInput(FILE_NAME);
            DataInputStream dis=new DataInputStream(fis);
            //creamos el array de bytes donde guardaremos los datos para luego mostrar
            byte[] buff= new byte[1000];
            //leeemos los datos
            dis.read(buff);
            //creamos un scroll del textview por si el texto es muy largo.
            mostrar.setMovementMethod(new ScrollingMovementMethod());
            // añadimos el texto guardado al textview
            mostrar.setText(new String(buff));
            //cerraamos el fileinputstream
            fis.close();
        // capturamos los posibles errores y mostramos un toast con lo que ha sucedido.
        }catch (FileNotFoundException e){
            Toast.makeText(MainActivity.this, "No existe el fichero espedificado", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(MainActivity.this, "Error al intentar leer el archivo", Toast.LENGTH_SHORT).show();
        }
    }
    public void grabarDatos(){

        try{
            //Intentamos crear el fileoutpput del archivo a escribir
            FileOutputStream fos=openFileOutput(FILE_NAME, Context.MODE_APPEND);
            //Creamos el string con la los datos del textview
            String cadena=texto.getText().toString();
            //Intentamos crear el stream de salida de datos
            DataOutputStream dos=new DataOutputStream(fos);
            //escribimos los datos en el fichero
            dos.writeBytes(cadena);
            //cerramos el fichero
            fos.close();
            //tratamos los posibles errores
        }catch (FileNotFoundException e){
            Toast.makeText(MainActivity.this, "No existe el fichero espedificado", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(MainActivity.this, "Error al intentar escribir en el archivo", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
