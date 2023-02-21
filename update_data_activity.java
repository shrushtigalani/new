package com.example.user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class update_data_activity extends AppCompatActivity {

    EditText firstname, mail, lastname, mobileno;
    Button update_data;
    com.example.user.DatabaseHelperClass databaseHelperClass;
    String fname, lname, email, date, data;
    int id;
    String number;
    String gender, hobby;
    TextView hobby1;
    CheckBox dance, read, swim;
    RadioGroup radioGroup;
    RadioButton male, rbFemale;
    Button pickDateBtn;
    TextView selectedDateTV, texterror;
    Spinner spiner;
    ImageView set_image_user;
    String[] str = {"camera", "gallery"};
    private int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;
    Uri image_uri;
    byte[] imageInByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data_activity);

        dance = findViewById(R.id.dance);
        read = findViewById(R.id.read);
        swim = findViewById(R.id.swim);
        male = findViewById(R.id.male);
        rbFemale = findViewById(R.id.female);
        hobby1 = findViewById(R.id.hobby1);
        update_data = findViewById(R.id.update_data);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        mail = findViewById(R.id.mail);
        mobileno = findViewById(R.id.mobileno);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);
        spiner = findViewById(R.id.spiner);
        texterror = findViewById(R.id.texterror);
        radioGroup = findViewById(R.id.radioGroup);
        databaseHelperClass = new com.example.user.DatabaseHelperClass(update_data_activity.this);

        id = getIntent().getIntExtra("Id",0);
        fname = getIntent().getStringExtra("First Name");
        lname = getIntent().getStringExtra("Last Name");
        email = getIntent().getStringExtra("Email ID");
        number = getIntent().getStringExtra("Mobile Number");
        gender = getIntent().getStringExtra("Gender");
        hobby = getIntent().getStringExtra("Hobby");
        date = getIntent().getStringExtra("Birth Date");
        data = getIntent().getStringExtra("Country");
        imageInByte=getIntent().getByteArrayExtra("bmp");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte , 0, imageInByte .length);
        set_image_user=findViewById(R.id.set_image_user);
        set_image_user.setImageBitmap(bitmap);


        String[] arrayOfString = hobby.split(" ");
        for(int i=0;i<arrayOfString.length;i++)
        {
            if(arrayOfString[i].equals("Swiming"))
            {
                swim.setChecked(true);
            }
            if(arrayOfString[i].equals("Dancing"))
            {
                dance.setChecked(true);
            }
            if(arrayOfString[i].equals("Reading"))
            {
                read.setChecked(true);
            }
        }

        firstname.setText(fname);
        lastname.setText(lname);
        mail.setText(email);
        mobileno.setText(number);
        selectedDateTV.setText(date);

        radioGroup.clearCheck();
        if (gender.equals("Female")) {
            rbFemale.setChecked(true);
        } else {
            male.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });

            if(data.equals("Mumbai"))
                spiner.setSelection(1);
            if(data.equals("channai"))
                spiner.setSelection(2);
            if(data.equals("Jaipur"))
                spiner.setSelection(3);
            if(data.equals("Surat"))
                spiner.setSelection(4);
            if(data.equals("Pune"))
                spiner.setSelection(5);
            if(data.equals("Bhopal"))
                spiner.setSelection(6);
            if(data.equals("Ahemdabad"))
                spiner.setSelection(7);
            if(data.equals("Vadodra"))
                spiner.setSelection(8);

            update_data.setOnClickListener(v -> {
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                String MobilePattern = "[0-9]{10}";
                final String namepattern = "[a-zA-Z]+\\.?";

                String fname = firstname.getText().toString().trim();
                String lname = lastname.getText().toString().trim();
                String email = mail.getText().toString().trim();
                String number = mobileno.getText().toString().trim();
                String date = selectedDateTV.getText().toString().trim();
                String data = spiner.getSelectedItem().toString().trim();

                if (fname != null && !fname.isEmpty() && !fname.equals("null")) {
                    if (fname.matches(namepattern)) {
                        if (lname != null && !lname.isEmpty() && !lname.equals("null")) {
                            if (lname.matches(namepattern)) {
                                if (email.matches(emailPattern)) {
                                    if (number.matches(MobilePattern)) {
                                        if (date != null && !date.isEmpty() && !date.equals("null")) {
                                            hobby = "";
                                            if (swim.isChecked() || dance.isChecked() || read.isChecked()) {
                                                if (swim.isChecked()) {
                                                    hobby += "Swiming ";
                                                }
                                                if (dance.isChecked()) {
                                                    hobby += "Dancing ";
                                                }
                                                if (read.isChecked()) {
                                                    hobby += "Reading ";
                                                }
                                                if (spiner.getSelectedItemPosition() != 0) {

                                                    databaseHelperClass.updateuser(new com.example.user.userModelClass(id,fname,lname,email,number,date,gender,hobby,data,imageInByte));
                                                    Intent i = new Intent(update_data_activity.this, com.example.user.viewdata.class);
                                                    startActivity(i);
                                                    finish();

                                                } else {
                                                    texterror.setError(getString(R.string.error_invalid_city));
                                                }
                                            } else {
                                                hobby1.setError(getString(R.string.error_invalid_hobby));
                                            }
                                        } else {
                                            selectedDateTV.setError(getString(R.string.error_invalid_date));
                                        }
                                    } else {
                                        mobileno.setError(getString(R.string.error_invalid_number));
                                    }
                                } else {
                                    mail.setError(getString(R.string.error_invalid_mail));
                                }
                            } else {
                                lastname.setError(getString(R.string.error_invalid_value));
                            }
                        } else {
                            lastname.setError(getString(R.string.error_invalid_name));
                        }
                    } else {
                        firstname.setError(getString(R.string.error_invalid_value));
                    }
                } else {
                    firstname.setError(getString(R.string.error_invalid_name));
                }

            });

            pickDateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedDateTV.setError(null);
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            update_data_activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    selectedDateTV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            },
                            year, month, day);
                    datePickerDialog.show();
                }
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

