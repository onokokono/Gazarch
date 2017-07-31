package com.example.marceline.gazarch.Login.Fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.marceline.gazarch.R;

public class SignUp_01 extends Fragment {

    private Button SignUpFragment_Nextbtn;
    private EditText UsernameText, LastnameText, FirstnameText, eMailText, PasswordText, PasswordRepeatText;
    private View MainView;
    private ImageView SignUpFragment_01_BackBtn;

    public SignUp_01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_01, container, false);
        MainView = (FrameLayout)view.findViewById(R.id.SignUpFragment_01_Root);;

        UsernameText = (EditText)view.findViewById(R.id.SignUpFragmentUsernameTxt);
        LastnameText = (EditText)view.findViewById(R.id.SignUpFragmentLastnametxt);
        FirstnameText = (EditText)view.findViewById(R.id.SignUpFragmentFirstnameTxt);
        eMailText = (EditText)view.findViewById(R.id.SignUpFragmentFirstnameTxt);
        PasswordText = (EditText)view.findViewById(R.id.SignUpFragmentPasswordTxt);
        PasswordRepeatText = (EditText)view.findViewById(R.id.SignUpFragmentPasswordRepeatTxt);

        SignUpFragment_01_BackBtn = (ImageView)view.findViewById(R.id.SignUpFragment_01_BackBtn);
        SignUpFragment_01_BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        SignUpFragment_Nextbtn = (Button)view.findViewById(R.id.SignUpFragmentNextbtn);
        SignUpFragment_Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextFragment();
            }
        });

        return view;
    }

    private void NextFragment()
    {
        final String uName , lName , fName , eMail, password, rPassword;
        uName = UsernameText.getText().toString();
        lName = LastnameText.getText().toString();
        fName = FirstnameText.getText().toString();
        eMail = eMailText.getText().toString();
        password = PasswordText.getText().toString();
        rPassword = PasswordRepeatText.getText().toString();
        if(TextUtils.isEmpty(uName) || TextUtils.isEmpty(lName) || TextUtils.isEmpty(fName) ||
                TextUtils.isEmpty(eMail) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rPassword))
        {
            Snackbar.make(MainView, "Талбаруудыг бүрэн бөглөнө үү!", Snackbar.LENGTH_LONG).show();
        }else{
            if(!password.equals(rPassword)){
                Snackbar.make(MainView, "Нууц үг давхцахгүй байна!", Snackbar.LENGTH_LONG).show();
            }else{
                Bundle bundle = new Bundle();
                bundle.putString("uName",uName);
                bundle.putString("lName",lName);
                bundle.putString("fName",fName);
                bundle.putString("eMail",eMail);
                bundle.putString("password",password);
                bundle.putString("rPassword",rPassword);

                SignUp_02 signUpFragment_02= new SignUp_02();
                signUpFragment_02.setArguments(bundle);
                replaceFragment(signUpFragment_02);
            }
        }
    }

    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.activity_sign_up_fragmentcontainer, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();



        }
    }

}
