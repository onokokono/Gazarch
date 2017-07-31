package com.example.marceline.gazarch.Login.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Region;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marceline.gazarch.Login.Model.RegisterModel;
import com.example.marceline.gazarch.Login.RegionSpinner.RegionSpinnerAdapter;
import com.example.marceline.gazarch.Login.RegionSpinner.RegionSpinnerItem;
import com.example.marceline.gazarch.Login.ServiceRequests.CustomRequest;
import com.example.marceline.gazarch.Login.ServiceRequests.RegisterRequest;
import com.example.marceline.gazarch.Login.ServiceRequests.SendDataToServer;
import com.example.marceline.gazarch.R;
import com.example.marceline.gazarch.Tools.Config;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class SignUp_02 extends Fragment {

    private CircleImageView profileimage;
    private ImageView SignUpFragment_02_BackBtn;
    private Uri profileImageUri = null;
    private static final int GALLERY_REQUEST = 1;

    Spinner GenderSpinner, RegionSpinner;
    private List<String> GenderList;
    private ArrayAdapter<String> GenderAdapter;

    private Calendar newCalendar;
    private DatePickerDialog BirthDatePickerDialog;
    TextView BirthYear, BirthMonth, BirthDay;
    private int year,month,day;
    private Bundle bundle;
    private ArrayList<RegionSpinnerItem> RegionSpinnerList;
    private RegionSpinnerAdapter RegionAdapter;

    private LinearLayout SignUpFragmentBirthDateLayout;
    private Button SignUpFragmentSignUpBtn;
    EditText SignUpFragmentPhonenumbertxt;
    private int mYear, mMonth, mDay;
    private RequestQueue RequestQueue;
    private ProgressDialog mProgress;

    RegisterModel registerModel;

    String username, email, first_name, last_name, password, password_repeat, gender, birth_date, phone_number, picture, region;

    String RESULT;

    public SignUp_02() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GenderList = new ArrayList<>();
        GenderList.add("Male");
        GenderList.add("Female");

        GenderAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, GenderList);
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        RegionSpinnerList = new ArrayList<>();

        RegionSpinnerList.add(new RegionSpinnerItem("Mongolia",R.drawable.ifmongolia298422));
        RegionSpinnerList.add(new RegionSpinnerItem("Korea",R.drawable.ifkoreasouth298472));
        RegionSpinnerList.add(new RegionSpinnerItem("Germany",R.drawable.ifgermany298451));
        //RegionSpinnerList.add(new RegionSpinnerItem("France",R.drawable.iffrance298425));

        RegionAdapter = new RegionSpinnerAdapter(getActivity(),
                R.layout.regionspinnerlayout,R.id.RegionSpinnerText,RegionSpinnerList);

        RequestQueue = Volley.newRequestQueue(getActivity());

        mProgress = new ProgressDialog(getActivity());

        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_02, container, false);

        BirthYear = (TextView)view.findViewById(R.id.SignUpFragmentBirthYeartxt);
        BirthMonth = (TextView)view.findViewById(R.id.SignUpFragmentBirthMonthtxt);
        BirthDay = (TextView)view.findViewById(R.id.SignUpFragmentBirthDaytxt);

        SignUpFragmentPhonenumbertxt = (EditText)view.findViewById(R.id.SignUpFragmentPhonenumbertxt);

        GenderSpinner = (Spinner)view.findViewById(R.id.SignUpFragmentGenderSpinner);
        GenderSpinner.setAdapter(GenderAdapter);

        RegionSpinner = (Spinner)view.findViewById(R.id.SignUpFragmentRegionsSpinner);
        RegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String r = RegionSpinnerList.get(i).getText();
                if(r.equals("Mongolia"))
                    SignUpFragmentPhonenumbertxt.setHint("(+976) Phone number");
                if(r.equals("Korea"))
                    SignUpFragmentPhonenumbertxt.setHint("(+82) Phone number");
                if(r.equals("Germany"))
                    SignUpFragmentPhonenumbertxt.setHint("(+49) Phone number");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RegionSpinner.setAdapter(RegionAdapter);

        SignUpFragment_02_BackBtn = (ImageView)view.findViewById(R.id.SignUpFragment_02_BackBtn);
        SignUpFragment_02_BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        SignUpFragmentBirthDateLayout = (LinearLayout)view.findViewById(R.id.SignUpFragmentBirthDateLayout);
        SignUpFragmentBirthDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDateDialog();
            }
        });

        SignUpFragmentSignUpBtn = (Button)view.findViewById(R.id.SignUpFragmentSignUpBtn);
        SignUpFragmentSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = bundle.getString("uName");
                email = bundle.getString("eMail");
                first_name = bundle.getString("fName");
                last_name = bundle.getString("lName");
                password = bundle.getString("password");
                password_repeat = bundle.getString("rPassword");
                gender = GenderSpinner.getSelectedItem().toString();
                region = RegionSpinnerList.get(RegionSpinner.getSelectedItemPosition()).getText();
                birth_date = BirthYear.getText() + "-" + BirthMonth.getText() + "-" + BirthDay.getText();
                phone_number = region+SignUpFragmentPhonenumbertxt.getText();

                JSONObject js = new JSONObject();
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.accumulate("username",username);
                    jsonObject.accumulate("first_name",first_name);
                    jsonObject.accumulate("last_name",last_name);
                    jsonObject.accumulate("email",email);
                    jsonObject.accumulate("password",password);
                    jsonObject.accumulate("password_repeat",password_repeat);
                    jsonObject.accumulate("gender",gender);
                    jsonObject.accumulate("phone_number",phone_number);
                    jsonObject.accumulate("picture",picture);
                    jsonObject.accumulate("birth_date",birth_date);

                    js.put("Data",jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(), jsonObject.toString(), Toast.LENGTH_LONG).show();

                if(TextUtils.isEmpty(gender)) return;
                if(TextUtils.isEmpty(birth_date)) return;

                new SendDataToServer().execute(String.valueOf(jsonObject));

                /*RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                CustomRequest jsObjRequest = new CustomRequest(Config.REGISTER_REQUEST_URL, js,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Error : " + error, Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                });

                requestQueue.add(jsObjRequest);*/

                RegisterRequest registerRequest = null;
                try {
                    registerRequest = new RegisterRequest(username, email, first_name, last_name, password, password_repeat, gender,birth_date, phone_number,""
                            ,new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getActivity(),"Respone : " + response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            ,new Response.ErrorListener()
                                    {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), "Error : " + error, Toast.LENGTH_SHORT).show();
                                            error.printStackTrace();
                                        }
                                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(registerRequest);
            }
        });

        profileimage = (CircleImageView)view.findViewById(R.id.SignUpFragmentProfileImageView);
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imgUri = data.getData();
            CropImage.activity(imgUri)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(getContext(),SignUp_02.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profileImageUri = result.getUri();
                profileimage.setImageURI(profileImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void showStartDateDialog(){
        //DialogFragment dialogFragment = new StartDatePicker();
        //dialogFragment.show(getFragmentManager(), "start_date_picker");

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;

                BirthYear.setText(Integer.toString(mYear));
                BirthMonth.setText(Integer.toString(mMonth+1));
                BirthDay.setText(Integer.toString(mDay));
            }
        };

        DatePickerDialog d1 = new DatePickerDialog(getActivity(),
                R.style.AppTheme_NoActionBar, mDateSetListener, 1980, 0, 1);

        DatePickerDialog d = new DatePickerDialog(getActivity(), mDateSetListener, 1980, 0, 1);
        d.show();
    }

    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, 1990, 1, 1);
            return dialog;

        }
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            BirthYear.setText(year);
            BirthMonth.setText(monthOfYear);
            BirthDay.setText(dayOfMonth);
        }
    }

    private static String POST(String URL, RegisterModel model)
    {
        InputStream inputStream = null;
        String result = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost();

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username",model.getUsername());
            jsonObject.accumulate("first_name",model.getFirst_name());
            jsonObject.accumulate("last_name",model.getLast_name());
            jsonObject.accumulate("email",model.getEmail());
            jsonObject.accumulate("password",model.getPassword());
            jsonObject.accumulate("password_repeat",model.getPassword_repeat());
            jsonObject.accumulate("gender",model.getGender());
            jsonObject.accumulate("phone_number",model.getPhone_number());
            jsonObject.accumulate("picture",model.getPicture());
            jsonObject.accumulate("birth_date",model.getBirth_date());

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Content-Type","JSON");

            HttpResponse httpResponse = httpClient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Result : Алдаа";
        }
        catch (Exception ex)
        {

        }
        finally
        {
            return result;
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            registerModel = new RegisterModel();

            registerModel.setFirst_name(username);
            registerModel.setBirth_date(birth_date);
            registerModel.setEmail(email);
            registerModel.setGender(gender);
            registerModel.setLast_name(last_name);
            registerModel.setFirst_name(first_name);
            registerModel.setPassword(password);
            registerModel.setPassword_repeat(password_repeat);
            registerModel.setPhone_number(phone_number);
            registerModel.setPicture(picture);
            RESULT = POST(urls[0],registerModel);
            return RESULT;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), RESULT, Toast.LENGTH_SHORT).show();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    private void makeJsonObjReq(Map<String,String> postParams) throws JSONException {
        showProgressDialog();

        registerModel = new RegisterModel();

        registerModel.setFirst_name(username);
        registerModel.setBirth_date(birth_date);
        registerModel.setEmail(email);
        registerModel.setGender(gender);
        registerModel.setLast_name(last_name);
        registerModel.setFirst_name(first_name);
        registerModel.setPassword(password);
        registerModel.setPassword_repeat(password_repeat);
        registerModel.setPhone_number(phone_number);
        registerModel.setPicture(picture);

        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("username",registerModel.getUsername());
        jsonObject.accumulate("first_name",registerModel.getFirst_name());
        jsonObject.accumulate("last_name",registerModel.getLast_name());
        jsonObject.accumulate("email",registerModel.getEmail());
        jsonObject.accumulate("password",registerModel.getPassword());
        jsonObject.accumulate("password_repeat",registerModel.getPassword_repeat());
        jsonObject.accumulate("gender",registerModel.getGender());
        jsonObject.accumulate("phone_number",registerModel.getPhone_number());
        jsonObject.accumulate("picture",registerModel.getPicture());
        jsonObject.accumulate("birth_date",registerModel.getBirth_date());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Config.REGISTER_REQUEST_URL, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "RESPONE : "+response.toString());
                        //msgResponse.setText(response.toString());
                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        hideProgressDialog();
                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(), "ERROR : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag(TAG);
        // Adding request to request queue
        RequestQueue.add(jsonObjReq);

        // Cancelling request
    /* if (queue!= null) {
    queue.cancelAll(TAG);
    } */

    }

    private void showProgressDialog() {
        mProgress.setMessage("Түр хүлээнэ үү?");
        mProgress.show();
    }
    private void hideProgressDialog(){
        mProgress.dismiss();
    }


}


