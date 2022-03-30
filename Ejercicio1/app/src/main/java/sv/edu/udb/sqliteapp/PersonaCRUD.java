package sv.edu.udb.sqliteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonaCRUD extends AppCompatActivity {

    private EditText etCod,etNom,etApe,etEda,etTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_crud);

        etCod = (EditText) findViewById(R.id.etCod);
        etNom = (EditText) findViewById(R.id.etNom);
        etApe = (EditText) findViewById(R.id.etApe);
        etEda = (EditText) findViewById(R.id.etEda);
        etTel = (EditText) findViewById(R.id.etTel);
    }

    public void altaP(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        if(etCod.getText().toString().isEmpty() || etNom.getText().toString().isEmpty() || etApe.getText().toString().isEmpty() || etEda.getText().toString().isEmpty() || etTel.getText().toString().isEmpty() ){
            if (etCod.getText().toString().isEmpty()){
                etCod.setError("Ingrese un codigo");
            }
            if (etNom.getText().toString().isEmpty()){
                etNom.setError("Ingrese un nombre");
            }
            if (etApe.getText().toString().isEmpty()){
                etApe.setError("Ingrese un apellido");
            }
            if (etEda.getText().toString().isEmpty()){
                etEda.setError("Ingrese una edad");
            }
            if (etTel.getText().toString().isEmpty()){
                etTel.setError("Ingrese un telefono");
            }
        }else {
            String cod = etCod.getText().toString();
            String nombre = etNom.getText().toString();
            String apellido = etApe.getText().toString();
            String edad = etEda.getText().toString();
            String telefono = etTel.getText().toString();

            ContentValues registro = new ContentValues();

            registro.put("codigo", cod);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("edad", edad);
            registro.put("telefono", telefono);

            try {
                bd.insertOrThrow("persona", null, registro);
                bd.close();
                etCod.setText("");
                etNom.setText("");
                etApe.setText("");
                etEda.setText("");
                etTel.setText("");
                Toast.makeText(this, "Se cargaron los datos de la persona",Toast.LENGTH_SHORT).show();
            } catch (SQLiteException e) {
                Toast.makeText(this, "ERROR!! No se cargaron los datos de la persona " + e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void consultaporcodigoP(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        if (etCod.getText().toString().isEmpty()){
            etCod.setError("Ingrese un codigo");
        }
        else{
            String cod = etCod.getText().toString();
            Cursor fila = bd.rawQuery("select nombre,apellido,edad,telefono from persona where codigo=" + cod, null);
            if (fila.moveToFirst()) {
                etNom.setText(fila.getString(0));
                etApe.setText(fila.getString(1));
                etEda.setText(fila.getString(2));
                etTel.setText(fila.getString(3));
            } else
                Toast.makeText(this, "No existe una persona con dicho código",
                        Toast.LENGTH_SHORT).show();
            bd.close();
        }
    }

    public void bajaporcodigoP(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        if (etCod.getText().toString().isEmpty()){
            etCod.setError("Ingrese un codigo");
        }else{
            String cod= etCod.getText().toString();
            int cant = bd.delete("persona", "codigo=" + cod, null);
            bd.close();
            etCod.setText("");
            etNom.setText("");
            etApe.setText("");
            etEda.setText("");
            etTel.setText("");
            if (cant == 1)
                Toast.makeText(this, "Se borró a la persona con dicho código",
                        Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No existe una persona con dicho código",
                        Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacionP(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        if (etCod.getText().toString().isEmpty()){
            etCod.setError("Ingrese un codigo");
        }
        else{
            String cod = etCod.getText().toString();
            String nombre = etNom.getText().toString();
            String apellido = etApe.getText().toString();
            String edad = etEda.getText().toString();
            String telefono = etTel.getText().toString();
            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("edad", edad);
            registro.put("telefono", telefono);
            int cant = bd.update("persona", registro, "codigo=" + cod, null);
            bd.close();
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                        .show();
            else
                Toast.makeText(this, "no existe una persona con el código ingresado",
                        Toast.LENGTH_SHORT).show();
        }
    }

    public void IrArticulos(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}