package sv.edu.udb.guia12appcontentprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Formulario extends AppCompatActivity {

    private EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
    }
    public void alta(View v) {
        try {
            DatabaseHelper admin = new DatabaseHelper(this,"students", null, 1);

            SQLiteDatabase bd = admin.getWritableDatabase();

            String carnet = et1.getText().toString();
            String nombre = et2.getText().toString();
            String apellido = et3.getText().toString();

            ContentValues values = new ContentValues();
            values.put(StudentsContract.Columnas.NOMBRE, carnet);
            values.put(StudentsContract.Columnas.APELLIDO, apellido);
            values.put(StudentsContract.Columnas.CARNET, carnet);
            bd.insert(StudentsContract.STUDENTS, null, values);
            et1.setText("");
            et2.setText("");
            et3.setText("");
            Toast.makeText(this, "Se cargaron los datos del artículo",Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "ERROR!! No se cargaron los datos del artículo" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void ver(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}