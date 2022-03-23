package mobile.praktik.prak4_database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class DBController extends SQLiteOpenHelper {
    private static final String NAMA_DB = "barang.db";

    public DBController(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAMA_DB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE users (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE barang (" +
                "id_brg INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kode_brg TEXT UNIQUE, " +
                "nama TEXT, " +
                "harga FLOAT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users;");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS barang;");
        onCreate(sqLiteDatabase);
    }

    public void insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        this.getWritableDatabase().insert("users", "", contentValues);
    }

    public User auth(User user) {
        @SuppressLint("Recycle") Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM users WHERE username = ? AND password = ?", new String[]{user.getUsername(), user.getPassword()}
        );
        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            User result = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            return result;
        }
        return null;
    }

    public void insertBarang(String kode, String nama, float harga) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("kode_brg", kode);
        contentValues.put("nama", nama);
        contentValues.put("harga", harga);
        this.getWritableDatabase().insert("barang", "", contentValues);
    }

    public void deleteBarang(String kode) {
        this.getWritableDatabase().delete("barang", "kode_brg = ?", new String[]{kode});
    }

    public void updateBarang(String kode, float harga) {
        this.getWritableDatabase().execSQL("UPDATE barang SET harga = ? WHERE kode_brg = ?", new String[]{String.valueOf(harga), kode});
    }

    public void listBarang(TextView textView) {
        @SuppressLint("Recycle") Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM barang", null);
        textView.setText("");
        while(cursor.moveToNext()) {
            textView.append(cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getFloat(3) + "\n");
        }
    }
}
