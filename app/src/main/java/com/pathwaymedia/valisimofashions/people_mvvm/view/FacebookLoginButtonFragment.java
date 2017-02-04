package com.pathwaymedia.valisimofashions.people_mvvm.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pathwaymedia.valisimofashions.people_mvvm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import com.pathwaymedia.valisimofashions.people_mvvm.paystack.ApiClient;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.service.ApiService;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.login.widget.ProfilePictureView.TAG;


public class FacebookLoginButtonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LoginButton loginButton;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;


    public FacebookLoginButtonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FacebookLoginButtonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FacebookLoginButtonFragment newInstance(String param1, String param2) {
        FacebookLoginButtonFragment fragment = new FacebookLoginButtonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Callback registration
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login", "onSuccess");
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Login Successful...Verifying");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        String firstName = bFacebookData.getString("first_name");
                        String lastName = bFacebookData.getString("last_name");
                        String gender = bFacebookData.getString("gender");
                        String profilePic = bFacebookData.getString("profile_pic");
                        String email = bFacebookData.getString("email");
                        Paper.book().write("firstName", firstName); // Primitive
                        Paper.book().write("lastName", lastName); // Primitive
                        Paper.book().write("gender", gender); // Primitive
                        Paper.book().write("profilePic", profilePic); // Primitive
                        Paper.book().write("email", email); // Primitive

                        try {
                            ApiService apiService = new ApiClient().getApiService();
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("email", email);
                            apiService.createCustomer(params).enqueue(new Callback<PaystackResponse<Customer>>() {
                                @Override
                                public void onResponse(Call<PaystackResponse<Customer>> call, Response<PaystackResponse<Customer>> response) {
                                    //progressBar.setVisibility(View.GONE);
                                    //Toast.makeText(TestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    PaystackResponse<Customer> paystackResponse = response.body();
                                    //tv_response.setText(paystackResponse.getMessage() + "::" + paystackResponse.getData().getEmail());
                                    Paper.book().write("customerCode", paystackResponse.getData().getCustomerCode());
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getActivity(), DrawerActivity.class));
                                }

                                @Override
                                public void onFailure(Call<PaystackResponse<Customer>> call, Throwable t) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getActivity(), DrawerActivity.class));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            startActivity(new Intent(getActivity(), DrawerActivity.class));
                        }





                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel () {
                Log.d("Login", "onCancel");
            }

            @Override
            public void onError (FacebookException e){
                Log.d("Login", "onError " + e);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook_login_button, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            Log.d("FBBundle", bundle.toString());
            return bundle;
        }
        catch(JSONException e) {
            Log.d(TAG,"Error parsing JSON");
            return null;
        }
    }



}
