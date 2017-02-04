package com.pathwaymedia.valisimofashions.people_mvvm.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pathwaymedia.valisimofashions.people_mvvm.R;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import com.pathwaymedia.valisimofashions.people_mvvm.paystack.ApiClient;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.InitTransaction;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.ListSubscriptionResponse;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.model.SubscriptionDetail;
import com.pathwaymedia.valisimofashions.people_mvvm.paystack.service.ApiService;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscriptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout ll_notsubscribed, ll_subscribed;
    Button bt_subscribe;
    ProgressBar pb;
    SubscriptionDetail mSubscriptionDetail = new SubscriptionDetail();
    TextView tv_amount, tv_start, tv_end, tv_plan_name;

    public SubscriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscriptionFragment newInstance(String param1, String param2) {
        SubscriptionFragment fragment = new SubscriptionFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);
        ll_notsubscribed = (LinearLayout)view.findViewById(R.id.ll_notsubscribed);
        ll_subscribed = (LinearLayout)view.findViewById(R.id.ll_subscribed);
        bt_subscribe = (Button)view.findViewById(R.id.bt_subscribe);
        pb = (ProgressBar)view.findViewById(R.id.pb);
        tv_amount = (TextView)view.findViewById(R.id.tv_amount);
        tv_start = (TextView)view.findViewById(R.id.tv_start);
        tv_plan_name = (TextView)view.findViewById(R.id.tv_planName);
        tv_end = (TextView)view.findViewById(R.id.tv_next_payment_date);

        ll_subscribed.setVisibility(View.GONE);
        ll_notsubscribed.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        checkSubscription();
        return view;
    }

    private void checkSubscription() {

        try {
            final String email = Paper.book().read("email", null);

            //create or fetch customer
            ApiService apiService = new ApiClient().getApiService();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            apiService.createCustomer(params).enqueue(new Callback<PaystackResponse<Customer>>() {
                @Override
                public void onResponse(Call<PaystackResponse<Customer>> call, Response<PaystackResponse<Customer>> response) {
                    PaystackResponse<Customer> response1 = response.body();
                    try {
                        final String email = Paper.book().read("email", null);

                        //init transaction
                        ApiService apiService = new ApiClient().getApiService();
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("customer", String.valueOf(response1.getData().getId()));
                        apiService.listSubscription(params).enqueue(new Callback<ListSubscriptionResponse>() {
                            @Override
                            public void onResponse(Call<ListSubscriptionResponse> call, Response<ListSubscriptionResponse> response) {
                                List<SubscriptionDetail> detailList =  response.body().getData();
                                if(detailList!=null && detailList.size()>0){
                                    for(SubscriptionDetail sd : detailList){
                                        if(sd.getStatus().equalsIgnoreCase("active")){
                                            mSubscriptionDetail = sd;
                                            editView(true);
                                            break;
                                        }
                                    }

                                }else{
                                    editView(false);
                                }
                            }

                            @Override
                            public void onFailure(Call<ListSubscriptionResponse> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<PaystackResponse<Customer>> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void editView(Boolean isSubscribed) {
        if(isSubscribed){
            ll_subscribed.setVisibility(View.VISIBLE);
            tv_plan_name.setText(mSubscriptionDetail.getPlan().getName());
            tv_end.setText(mSubscriptionDetail.getNextPaymentDate());
            tv_start.setText(mSubscriptionDetail.getStart());
            tv_amount.setText(mSubscriptionDetail.getAmount());


        }else{
            //not subscribed yet
            ll_notsubscribed.setVisibility(View.VISIBLE);
            bt_subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pb.setVisibility(View.VISIBLE);
                    try {
                        final String email = Paper.book().read("email", null);

                        //init transaction
                        ApiService apiService = new ApiClient().getApiService();
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("plan", getString(R.string.planCode));
                        params.put("email", email);
                        params.put("amount", "10000");
                        apiService.initTransaction(params).enqueue(new Callback<PaystackResponse<InitTransaction>>() {
                            @Override
                            public void onResponse(Call<PaystackResponse<InitTransaction>> call, Response<PaystackResponse<InitTransaction>> response) {
                                //open url in browser
                                PaystackResponse<InitTransaction> response1 = response.body();
                                String url = response1.getData().getAuthorizationUrl();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);
                                getActivity().finish();
                            }
                            @Override
                            public void onFailure(Call<PaystackResponse<InitTransaction>> call, Throwable t) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
