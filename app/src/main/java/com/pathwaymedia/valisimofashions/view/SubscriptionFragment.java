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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pathwaymedia.valisimofashions.PeopleApplication;

import java.util.HashMap;
import java.util.List;

import com.pathwaymedia.valisimofashions.R;
import com.pathwaymedia.valisimofashions.paystack.ApiClient;
import com.pathwaymedia.valisimofashions.paystack.model.Customer;
import com.pathwaymedia.valisimofashions.paystack.model.GetSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.InitTransaction;
import com.pathwaymedia.valisimofashions.paystack.model.ListSubscriptionResponse;
import com.pathwaymedia.valisimofashions.paystack.model.PaystackResponse;
import com.pathwaymedia.valisimofashions.paystack.model.SubscriptionDetail;
import com.pathwaymedia.valisimofashions.paystack.service.ApiService;

import io.paperdb.Paper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    TextView tv_subscribe;
    ProgressBar pb;
    RadioButton rb_paystack;
    TextView tv_start, tv_end;
    GetSubscriptionResponse subscriptionResponse;

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
        rb_paystack = (RadioButton) view.findViewById(R.id.rb_paystack);
        pb = (ProgressBar)view.findViewById(R.id.pb);
        //tv_amount = (TextView)view.findViewById(R.id.tv_amount);
        tv_start = (TextView)view.findViewById(R.id.tv_start);
        //tv_plan_name = (TextView)view.findViewById(R.id.tv_planName);
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
            apiService.checkSubscription(params, new Callback<GetSubscriptionResponse>() {
                @Override
                public void success(GetSubscriptionResponse getSubscriptionResponse, Response response) {
                    subscriptionResponse = getSubscriptionResponse;
                    if(getSubscriptionResponse.getStatus().equalsIgnoreCase("active")){
                        editView(true);
                        PeopleApplication.subscribed = true;
                        Paper.book().write("subscribed", true);
                    }else{
                        editView(false);
                        PeopleApplication.subscribed = false;
                        Paper.book().write("subscribed", false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), "Error fetching subscription. Please try again after sometime.", Toast.LENGTH_SHORT).show();
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
            //tv_plan_name.setText("Valisimo Fashion");
            tv_end.setText(subscriptionResponse.getExpiry_date());
            tv_start.setText(subscriptionResponse.getStart_date());


        }else{
            //not subscribed yet
            ll_notsubscribed.setVisibility(View.VISIBLE);
            tv_subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pb.setVisibility(View.VISIBLE);
                    /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.subs_page_url)));
                    startActivity(browserIntent);
                    getActivity().finish();*/
                    final String email = Paper.book().read("email", null);
                    String firstName = Paper.book().read("firstName", "Hello");
                    String lastName = Paper.book().read("lastName", "Guest");

                    String url = "http://valisimofashions.com/api/subscribe?email="+email+"&first_name="+firstName+"&last_name="+lastName;
                    if(!rb_paystack.isChecked()){
                        url += ("&pay_mtd=paypal");
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    Intent webviewIntent = new Intent(getActivity(), PaystackWebViewActivity.class);
                    webviewIntent.putExtra("url", url);
                    startActivity(browserIntent);
                    pb.setVisibility(View.GONE);
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            });
        }
    }

}
