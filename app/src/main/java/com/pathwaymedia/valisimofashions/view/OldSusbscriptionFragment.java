package com.pathwaymedia.valisimofashions.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pathwaymedia.valisimofashions.PeopleApplication;
import com.pathwaymedia.valisimofashions.R;
import com.pathwaymedia.valisimofashions.paystack.ApiClient;
import com.pathwaymedia.valisimofashions.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.paystack.model.InitTransaction;
import com.pathwaymedia.valisimofashions.paystack.model.ListSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.paystack.model.SubscriptionDetail;
import com.pathwaymedia.valisimofashions.paystack.service.ApiService;

import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;
import retrofit.RetrofitError;

/**
 * Created by gulshanbudhwani on 21/03/17.
 */

public class OldSusbscriptionFragment extends Fragment {
    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout ll_notsubscribed, ll_subscribed;
    TextView tv_subscribe;
    ProgressBar pb;
    SubscriptionDetail mSubscriptionDetail = new SubscriptionDetail();
    TextView tv_amount, tv_start, tv_end, tv_plan_name;

    public OldSusbscriptionFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionFragment.
     *//*
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
        //Toast.makeText(getActivity(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);
        ll_notsubscribed = (LinearLayout)view.findViewById(R.id.ll_notsubscribed);
        ll_subscribed = (LinearLayout)view.findViewById(R.id.ll_subscribed);
        tv_subscribe = (TextView) view.findViewById(R.id.tv_subscribe);
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
            String email = Paper.book().read("email", null);
            //Toast.makeText(getActivity(), "checking Subscription for "+ email, Toast.LENGTH_SHORT).show();
            Log.d("Check Subs", email);
            //email = "abc@softkoki.com";
            //create or fetch customer
            ApiService apiService = ApiClient.getService();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", email);
            apiService.createCustomer(params, new retrofit.Callback<PaystackResponse<Customer>>() {
                @Override
                public void success(PaystackResponse<Customer> customerPaystackResponse, retrofit.client.Response response) {
                    //Toast.makeText(getActivity(), "Customer Fetched: " + customerPaystackResponse.getData().getCustomerCode(), Toast.LENGTH_SHORT).show();
                    try {
                        final String email = Paper.book().read("email", null);

                        //init transaction
                        ApiService apiService = ApiClient.getService();
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("customer", String.valueOf(customerPaystackResponse.getData().getId()));
                        apiService.listSubscription(params, new retrofit.Callback<ListSubscriptionResponse>() {
                            @Override
                            public void success(ListSubscriptionResponse listSubscriptionResponse, retrofit.client.Response response) {
                                List<SubscriptionDetail> detailList =  listSubscriptionResponse.getData();
                                //Toast.makeText(getActivity(), "Subscriptions fetched: "+String.valueOf(detailList.size()), Toast.LENGTH_SHORT).show();
                                boolean is_sub = false;
                                if(detailList!=null && detailList.size()>0){
                                    for(SubscriptionDetail sd : detailList){
                                        if(sd.getStatus().equalsIgnoreCase("active")){
                                            mSubscriptionDetail = sd;
                                            is_sub = true;
                                            break;
                                        }
                                    }

                                }
                                editView(is_sub);
                                PeopleApplication.subscribed = is_sub;
                                Paper.book().write("subscribed", is_sub); // Primitive

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                error.printStackTrace();
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void editView(Boolean isSubscribed) {
        //Toast.makeText(getActivity(), "editView: " + String.valueOf(isSubscribed), Toast.LENGTH_SHORT).show();
        pb.setVisibility(View.GONE);
        if(isSubscribed){
            ll_subscribed.setVisibility(View.VISIBLE);
            tv_plan_name.setText(mSubscriptionDetail.getPlan().getName());
            tv_end.setText(mSubscriptionDetail.getNextPaymentDate().substring(0,10));
            tv_start.setText(String.valueOf(mSubscriptionDetail.getCreatedAt().substring(0,10)));
            tv_amount.setText(String.valueOf(mSubscriptionDetail.getAmount()/100.00) + " Naira");


        }else{
            //not subscribed yet
            ll_notsubscribed.setVisibility(View.VISIBLE);
            tv_subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pb.setVisibility(View.VISIBLE);
                    *//*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.subs_page_url)));
                    startActivity(browserIntent);
                    getActivity().finish();*//*
                    try {
                        final String email = Paper.book().read("email", null);


                        //init transaction
                        ApiService apiService = ApiClient.getService();
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("plan", getString(R.string.planCode));
                        //params.put("plan", "PLN_udcapu7k1pqjh65");
                        params.put("email", email);
                        params.put("amount", "10000");
                        apiService.initTransaction(params, new retrofit.Callback<PaystackResponse<InitTransaction>>() {
                            @Override
                            public void success(PaystackResponse<InitTransaction> initTransactionPaystackResponse, retrofit.client.Response response) {
                                //open url in browser
                                //Toast.makeText(getActivity(), "url fetched: "+ initTransactionPaystackResponse.getData().getAuthorizationUrl(), Toast.LENGTH_SHORT).show();
                                String url = initTransactionPaystackResponse.getData().getAuthorizationUrl();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                Intent webviewIntent = new Intent(getActivity(), PaystackWebViewActivity.class);
                                webviewIntent.putExtra("url", url);
                                startActivity(browserIntent);
                                pb.setVisibility(View.GONE);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }*/

}
