package com.android.hackermonty;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class MainActivity extends AppCompatActivity implements ValueEventListener {
    private TextView HeadingText;
    private EditText HeadingInput;
    private RadioButton RbRed,RbBlue;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference  mRootReference=firebaseDatabase.getReference();
    private DatabaseReference  mHeadingReference = mRootReference.child("heading");
    private DatabaseReference  mFontColorReference=mRootReference.child("fontcolor");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HeadingText=(TextView)findViewById(R.id.headingText);
        HeadingInput=(EditText) findViewById(R.id.headingInput);
        RbRed=(RadioButton)findViewById(R.id.rbRed);
        RbBlue=(RadioButton)findViewById(R.id.rbBlue);
    }


    public void submitHeading(View view)
    {

        String heading=HeadingInput.getText().toString();
        mHeadingReference.setValue(heading);
        HeadingInput.setText("");
    }


    public void onRbtnclick(View view) {
        switch (view.getId())
        {
            case R.id.rbRed:
                mFontColorReference.setValue("red");
                break;

            case R.id.rbBlue:
                mFontColorReference.setValue("blue");
                break;
        }

    }


    @Override
    public  void onDataChange(DataSnapshot dataSnapshot) {

        if(dataSnapshot.getValue(String.class)!=null)
        {
            String key = dataSnapshot.getKey();

            if(key.equals("heading"))
            {
                String heading = dataSnapshot.getValue(String.class);
                HeadingText.setText(heading);
            }
            else if(key.equals("fontcolor"))
            {
                String color = dataSnapshot.getValue(String.class);
                if(color.equals("red"))
                {
                    HeadingText.setTextColor(ContextCompat.getColor(this,R.color.colorRed));
                    RbRed.setChecked(true);
                }
                else if(color.equals("blue"))
                {
                    HeadingText.setTextColor(ContextCompat.getColor(this,R.color.colorBlue));
                    RbBlue.setChecked(true);
                }


            }
        }

    }
    @Override
    public  void onCancelled(DatabaseError databaseError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHeadingReference.addValueEventListener(this);
        mFontColorReference.addValueEventListener(this);
    }


}
