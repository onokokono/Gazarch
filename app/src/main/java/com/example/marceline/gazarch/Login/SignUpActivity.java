
package com.example.marceline.gazarch.Login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.marceline.gazarch.Login.Fragment.SignInFragment;
import com.example.marceline.gazarch.Login.Fragment.SignUp_01;
import com.example.marceline.gazarch.R;

public class SignUpActivity extends AppCompatActivity {

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUp_01 signUpFragment_01 = new SignUp_01();
        replaceFragment(signUpFragment_01);
        isFirst = false;
    }

    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.activity_sign_up_fragmentcontainer, fragment);
            if(!isFirst)
                ft.addToBackStack(backStateName);
            ft.commit();



        }
    }
}
