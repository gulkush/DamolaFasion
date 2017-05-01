package com.pathwaymedia.valisimofashions.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.pathwaymedia.valisimofashions.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostWebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostWebViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PostWebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostWebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostWebViewFragment newInstance(String param1, String param2) {
        PostWebViewFragment fragment = new PostWebViewFragment();
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
        Toast.makeText(getActivity(), "Coming up embedded ytv", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_web_view, container, false);
        WebView webview = (WebView)view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        //webview.loadDataWithBaseURL("", PeopleApplication.current_post.getContent().getRendered(), "text/html", "UTF-8", "");
        String content = "<iframe width=\"100%\" height=\"315\"\n" +
                "src=\"https://www.youtube.com/embed/XGSy3_Czz8k\">\n" +
                "</iframe><hr>";
        webview.loadDataWithBaseURL("", content + content, "text/html", "UTF-8", "");
        webview.setWebViewClient(new WebViewClient());

        return view;
    }

}
