package com.example.biro.ptf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.biro.ptf.Contract.Constants;
import com.example.biro.ptf.Models.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteActivity extends AppCompatActivity {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.editEmail)
    EditText email;
    @BindView(R.id.editName)
    EditText name;
    @BindView(R.id.editGender)
    EditText gender;
    @BindView(R.id.editBirthDate)
    EditText birthdate;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.complete)
    Button complete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        ButterKnife.bind(this);
        final User currentUser = getIntent().getParcelableExtra(Constants.userKeys.key);
        email.setText(" " + currentUser.getEmail());
        name.setText(" " + currentUser.getFull_name());
        gender.setText(" "+ currentUser.getGender());
        mobile.setText(" "+currentUser.getMobilePhone());

        Picasso.with(this).load(currentUser.getProfile_pic_url()).into(profileImage);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CompleteActivity.this, MainActivity.class).putExtra(Constants.userKeys.key, currentUser));
            }
        });

    }
}
