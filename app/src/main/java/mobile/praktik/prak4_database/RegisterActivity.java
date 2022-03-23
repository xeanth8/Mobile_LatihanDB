package mobile.praktik.prak4_database;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private DBController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.edittext_username);
        etPassword = findViewById(R.id.edittext_password);

        controller = new DBController(this, null, 1);
    }

    public void register(View view) {
        try {
            if(etUsername.length() == 0) {
                Toast.makeText(RegisterActivity.this, "Please fill username.", Toast.LENGTH_SHORT).show();
            } else if(etPassword.length() == 0) {
                Toast.makeText(RegisterActivity.this, "Please fill password.", Toast.LENGTH_SHORT).show();
            } else {
                controller.insertUser(new User(null, etUsername.getText().toString(), etPassword.getText().toString()));
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (SQLiteException e) {
            Toast.makeText(RegisterActivity.this, "This username already exists.", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
