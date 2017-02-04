package com.pathwaymedia.valisimofashions.people_mvvm.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.rest.WpClientRetrofit;
import com.pathwaymedia.valisimofashions.people_mvvm.R;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.pathwaymedia.valisimofashions.people_mvvm.paystack.ApiClient;
import co.paystack.android.api.service.ApiService;
import co.paystack.android.model.Customer;
import co.paystack.android.model.PaystackResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestActivity extends AppCompatActivity {

    Button bt_fetch;
    WpClientRetrofit wpClientRetrofit;
    TextView tv_response;
    EditText et_email;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        bt_fetch = (Button)findViewById(R.id.bt_fetch);
        tv_response = (TextView)findViewById(R.id.tv_response);
        et_email = (EditText)findViewById(R.id.et_email);
        progressBar = (ProgressBar)findViewById(R.id.pb);
        wpClientRetrofit = new WpClientRetrofit(getString(R.string.wp_base_url), "gulshan", "gulkush88+");

        bt_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                try {
                    ApiService apiService = new ApiClient().getApiService();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("email", et_email.getText().toString());
                    apiService.createCustomer(params).enqueue(new Callback<PaystackResponse<Customer>>() {
                        @Override
                        public void onResponse(Call<PaystackResponse<Customer>> call, Response<PaystackResponse<Customer>> response) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            PaystackResponse<Customer> paystackResponse = response.body();
                            tv_response.setText(paystackResponse.getMessage() + "::" + paystackResponse.getData().getEmail());
                        }

                        @Override
                        public void onFailure(Call<PaystackResponse<Customer>> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
