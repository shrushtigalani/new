package com.example.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    private static final String DB_NAME = "user form";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "registration";
    private static final String ID_COL = "id";
    private static final String F_NAME = "firstname";
    private static final String L_NAME = "lastname";
    private static final String MAILID = "mailid";
    private static final String NUMBER = "phonenumber";
    private static final String BIRTHDATE = "birthdate";
    private static final String GENDER = "gender";
    private static final String HOBBY = "hobby";
    private static final String CITY = "city";
    private static final String USER_IMAGE_SHOW = "user_profile";
    private static final String TAG = "DatabaseHelper ";

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelperClass(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + F_NAME + " TEXT,"
                + L_NAME + " TEXT,"
                + MAILID + " TEXT,"
                + NUMBER + " TEXT,"
                + BIRTHDATE + " TEXT,"
                + GENDER + " INTEGER,"
                + HOBBY + " TEXT,"
                + CITY + " TEXT,"
                + USER_IMAGE_SHOW + " BLOB)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void adduser(com.example.user.userModelClass userModelClass){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelperClass.F_NAME, userModelClass.getFname());
        contentValues.put(DatabaseHelperClass.L_NAME, userModelClass.getLname());
        contentValues.put(DatabaseHelperClass.MAILID, userModelClass.getEmail());
        contentValues.put(DatabaseHelperClass.NUMBER, userModelClass.getNumber());
        contentValues.put(DatabaseHelperClass.BIRTHDATE, userModelClass.getDate());
        contentValues.put(DatabaseHelperClass.GENDER, userModelClass.getGender());
        contentValues.put(DatabaseHelperClass.HOBBY, userModelClass.getHobby());
        contentValues.put(DatabaseHelperClass.CITY, userModelClass.getData());
        contentValues.put(DatabaseHelperClass.USER_IMAGE_SHOW, userModelClass.getImage());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelperClass.TABLE_NAME, null,contentValues);
    }

    public List<com.example.user.userModelClass> readusers() {
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<com.example.user.userModelClass> storeuser = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                System.out.println("id="+id);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String email = cursor.getString(3);
                String number = cursor.getString(4);
                String date = cursor.getString(5);
                String gender = cursor.getString(6);
                String hobby = cursor.getString(7);
                String data = cursor.getString(8);
                byte[] image = cursor.getBlob(9);

                storeuser.add(new com.example.user.userModelClass(id,fname,lname,email,number,date,gender,hobby,data,image));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeuser;
    }

    public void deleteuser(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID_COL + " = ? ", new String[]
                {String.valueOf(id)});
    }

    public void updateuser(com.example.user.userModelClass userModelClass) {
    //    SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.F_NAME,userModelClass.getFname());
        contentValues.put(DatabaseHelperClass.L_NAME,userModelClass.getLname());
        contentValues.put(DatabaseHelperClass.MAILID,userModelClass.getEmail());
        contentValues.put(DatabaseHelperClass.NUMBER,userModelClass.getNumber());
        contentValues.put(DatabaseHelperClass.BIRTHDATE,userModelClass.getDate());
        contentValues.put(DatabaseHelperClass.GENDER,userModelClass.getGender());
        contentValues.put(DatabaseHelperClass.HOBBY,userModelClass.getHobby());
        contentValues.put(DatabaseHelperClass.CITY,userModelClass.getData());
        contentValues.put(DatabaseHelperClass.USER_IMAGE_SHOW, userModelClass.getImage());

        sqLiteDatabase = this.getWritableDatabase();
       int result=  sqLiteDatabase.update(TABLE_NAME,contentValues,ID_COL + " = ?" , new String[]
                {String.valueOf(userModelClass.getId())});

        Log.e(TAG+ ":",""+result );
        Log.e(TAG+ ":",""+userModelClass.getId() );

    }
}
























