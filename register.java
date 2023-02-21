package com.example.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;


public class register extends AppCompatActivity  implements
        AdapterView.OnItemSelectedListener {


    private static final int GALLERY_REQUEST = 3;
    EditText firstname, mail, lastname, mobileno;
    Button submit, pickDateBtn;
    RadioGroup radioGroup;
    String gender = "Male";
    RadioButton male, female;
    CheckBox dance, read, swim;
    Spinner spiner;
    TextView selectedDateTV, texterror, hobby1;
    SharedPreferences sharedPreferences;
    com.example.user.DatabaseHelperClass databaseHelperClass;
    String fname, lname, email, number, date, data, hobby;
    ImageView set_image_user;
    String[] str = {"camera", "gallery"};
    private int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;
    Uri image_uri;
    byte[] imageInByte;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        set_image_user = findViewById(R.id.set_image_user);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);
        submit = findViewById(R.id.submit);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        mail = findViewById(R.id.mail);
        mobileno = findViewById(R.id.mobileno);
        hobby1 = findViewById(R.id.hobby1);
        dance = findViewById(R.id.dance);
        read = findViewById(R.id.read);
        swim = findViewById(R.id.swim);
        radioGroup = findViewById(R.id.radioGroup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        texterror = findViewById(R.id.texterror);

        spiner = findViewById(R.id.spiner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.android_dropdown_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);

        sharedPreferences = getSharedPreferences("Registration", 0);
        databaseHelperClass = new com.example.user.DatabaseHelperClass(register.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mail.setSingleLine(true);
        mail.setHorizontallyScrolling(false);
        mail.setMaxLines(1);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.male) {
                gender = "Male";
            } else {
                gender = "Female";
            }
        });

        pickDateBtn.setOnClickListener(v -> {
            selectedDateTV.setError(null);
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    register.this,
                    (view, year1, monthOfYear, dayOfMonth) -> selectedDateTV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1),
                    year, month, day);
            datePickerDialog.show();
        });

        submit.setOnClickListener(v -> {

            String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            String MobilePattern = "[0-9]{10}";
            final String namepattern = "[a-zA-Z]+\\.?";

            fname = firstname.getText().toString().trim();
            lname = lastname.getText().toString().trim();
            email = mail.getText().toString().trim();
            number = mobileno.getText().toString().trim();
            date = selectedDateTV.getText().toString().trim();
            data = spiner.getSelectedItem().toString().trim();


            if(imageInByte!=null){
                if (fname != null && !fname.isEmpty() && !fname.equals("null")) {
                    if (fname.matches(namepattern)) {
                        if (lname != null && !lname.isEmpty() && !lname.equals("null")) {
                            if (lname.matches(namepattern)) {
                                if (email.matches(emailPattern)) {
                                    if (number.matches(MobilePattern)) {
                                        if (date != null && !date.isEmpty() && !date.equals("null")) {
                                            hobby = "";
                                            hobby1.setError(null);
                                            if (swim.isChecked() || dance.isChecked() || read.isChecked()) {
                                                if (swim.isChecked()) {
                                                    hobby += "Swiming  ";}
                                                if (dance.isChecked()) {
                                                    hobby += "Dancing  ";}
                                                if (read.isChecked()) {
                                                    hobby += "Reading  ";}
                                                if (spiner.getSelectedItemPosition() != 0) {
                                                    SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("User_profile_image", String.valueOf(imageInByte));
                                                    editor.putString("Firstname", fname);
                                                    editor.putString("lastname", lname);
                                                    editor.putString("Email", email);
                                                    editor.putString("mobimenum", number);
                                                    editor.putString("birthday", date);
                                                    editor.putString("Gender", gender);
                                                    editor.putString("Hobby", hobby);
                                                    editor.putString("City", data);
                                                    com.example.user.DatabaseHelperClass databaseHelperClass = new com.example.user.DatabaseHelperClass(register.this);
                                                    com.example.user.userModelClass userModelClass = new com.example.user.userModelClass(fname, lname, email, number, date, gender, hobby, data,imageInByte);
                                                    databaseHelperClass.adduser(userModelClass);
                                                    Intent intent = new Intent(register.this, viewdata.class);
                                                    editor.putBoolean("Bool", true);
                                                    editor.commit();
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    texterror.setError(getString(R.string.error_invalid_city));}
                                            } else {
                                                hobby1.setError(getString(R.string.error_invalid_hobby));}
                                        } else {
                                            selectedDateTV.setError(getString(R.string.error_invalid_date));}
                                    } else {
                                        mobileno.setError(getString(R.string.error_invalid_number));}
                                } else {
                                    mail.setError(getString(R.string.error_invalid_mail));}
                            } else {
                                lastname.setError(getString(R.string.error_invalid_value));}
                        } else {
                            lastname.setError(getString(R.string.error_invalid_name));}
                    } else {
                        firstname.setError(getString(R.string.error_invalid_value));}
                } else {
                    firstname.setError(getString(R.string.error_invalid_name));}
            } else {
                Snackbar.make(set_image_user, "Please Add Your Image",
                        Snackbar.LENGTH_SHORT).show();}
        });



        set_image_user.setOnClickListener(view -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(str, (dialogInterface, i) -> {
            if (i == 0) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);}
            if (i == 1) {
                Intent imageintent = new Intent();
                imageintent.setType("image/*");
                imageintent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imageintent, "Select Picture"), PICK_IMAGE);}
        });
        builder.setTitle("Select Image");
        builder.show();
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            set_image_user.setImageBitmap(bitmap);
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream2);
            imageInByte = stream2.toByteArray();
            BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        }
        if (requestCode == PICK_IMAGE) {
            image_uri = data.getData();
            try {
                Bitmap bitmap;
                InputStream stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                set_image_user.setImageBitmap(bitmap);
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream1);
                imageInByte = stream1.toByteArray();
                BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Show_data_itemmenu:
                Intent intent = new Intent(register.this, viewdata.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


}