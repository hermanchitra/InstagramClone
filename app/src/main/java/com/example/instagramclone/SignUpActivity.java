package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;
    private Button btnGetAllData, btnTransition;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnTransition);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("OjaPLMq4jm", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText(object.get("name").toString());
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.whereGreaterThanOrEqualTo("punchPower", 1500);
                queryAll.setLimit(1);
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                for (ParseObject object: objects) {
                                    allKickBoxers += object.get("name") + "\n";
                                }
                                FancyToast.makeText(SignUpActivity.this, allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            }
                        } else {
                            FancyToast.makeText(SignUpActivity.this, "Failed " + e.getLocalizedMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignUpLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void helloWorldTapped(View view) {
//        ParseObject boxer = new ParseObject("Boxer");
//        boxer.put("punch_speed", 200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Toast.makeText(SignUpActivity.this, "boxer object is saved successfully", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(SignUpActivity.this, "Saving failed" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name", "John");
        kickBoxer.put("punchSpeed", 1000);
        kickBoxer.put("punchPower", 2000);
        kickBoxer.put("kickSpeed", 3000);
        kickBoxer.put("kickPower", 4000);
        kickBoxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignUpActivity.this, kickBoxer.get("name") +" is saved to server", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Saving failed" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void saveKickBoxer(View view) {
        final ParseObject kickBoxer = new ParseObject("KickBoxer");
        try {
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUpActivity.this, kickBoxer.get("name") + " is saved to server", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        FancyToast.makeText(SignUpActivity.this, "Saving failed. " + e.getLocalizedMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUpActivity.this, e.getLocalizedMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }
}