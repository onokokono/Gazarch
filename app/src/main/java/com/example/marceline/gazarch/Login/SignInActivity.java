package com.example.marceline.gazarch.Login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.marceline.gazarch.Login.Fragment.SignInFragment;
import com.example.marceline.gazarch.Login.Fragment.SignUp_01;
import com.example.marceline.gazarch.R;

public class SignInActivity extends AppCompatActivity {

    private Button SignUpBtn;
    private Boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        SignUpBtn = (Button)findViewById(R.id.activity_sign_in_signupbtn);

        ButtonEvents();

        SignInFragment SignInFragment = new SignInFragment();
        replaceFragment(SignInFragment);
        isFirst = false;


    }

    private void ButtonEvents()
    {
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignUpActivity = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(SignUpActivity);
            }
        });
    }

    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.activity_sign_in_fragmentContainer, fragment);
            if(!isFirst)
                ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
