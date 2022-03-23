package mobile.praktik.prak4_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText etKode, etNama, etHarga;
    private TextView tvCurrentUser, tvData;
    private DBController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        tvCurrentUser = findViewById(R.id.textview_currentuser);
        etKode = findViewById(R.id.edittext_kodeBarang);
        etNama = findViewById(R.id.edittext_namaBarang);
        etHarga = findViewById(R.id.edittext_hargaBarang);
        tvData = findViewById(R.id.textview_listbarang);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            tvCurrentUser.setText("Hello, " + bundle.getString("username"));
        }

        controller = new DBController(this, null, 1);
    }

    public void add(View view) {
        try {
            if(etKode.length() == 0) {
                Toast.makeText(MainActivity.this, "Mohon isi kode barang.", Toast.LENGTH_SHORT).show();
            } else if(etNama.length() == 0) {
                Toast.makeText(MainActivity.this, "Mohon isi nama barang.", Toast.LENGTH_SHORT).show();
            } else if(etHarga.length() == 0) {
                Toast.makeText(MainActivity.this, "Mohon isi harga barang.", Toast.LENGTH_SHORT).show();
            } else {
                controller.insertBarang(etKode.getText().toString(), etNama.getText().toString(), Float.parseFloat(String.valueOf(etHarga.getText())));
                Toast.makeText(MainActivity.this, "Penambahan data berhasil.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {
            Toast.makeText(MainActivity.this, "Data sudah ada.", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(View view) {
        if(etKode.length() == 0) {
            Toast.makeText(MainActivity.this, "Mohon isi kode barang.", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Harga baru");
            final EditText etHargaBaru = new EditText(MainActivity.this);
            dialog.setView(etHargaBaru);

            dialog.setPositiveButton("OK", (
                    (dialogInterface, i) -> controller.updateBarang(
                            etKode.getText().toString(),
                            Float.parseFloat(String.valueOf(etHargaBaru.getText()))
                    ))
            );

            dialog.show();
        }
    }

    public void delete(View view) {
        if(etKode.length() == 0) {
            Toast.makeText(MainActivity.this, "Mohon isi kode barang.", Toast.LENGTH_SHORT).show();
        } else {
            controller.deleteBarang(etKode.getText().toString());
        }
    }

    public void view(View view) {
        controller.listBarang(tvData);
    }

    public void logout (View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}