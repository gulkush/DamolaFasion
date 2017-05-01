package com.pathwaymedia.valisimofashions.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afrozaar.wp_api_v2_client_android.model.Post;
import com.afrozaar.wp_api_v2_client_android.rest.HttpServerErrorResponse;
import com.afrozaar.wp_api_v2_client_android.rest.WordPressRestResponse;
import com.afrozaar.wp_api_v2_client_android.rest.WpClientRetrofit;
import com.pathwaymedia.valisimofashions.R;
import com.pathwaymedia.valisimofashions.adapter.PostsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllPostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPostsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private boolean mParam2;

    RecyclerView recyclerView;
    PostsAdapter adapter;
    List<Post> posts = new ArrayList<>();
    WpClientRetrofit wpClientRetrofit;
    ProgressBar pb;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int page = 1;


    public AllPostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllPostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllPostsFragment newInstance(String param1, boolean param2) {
        AllPostsFragment fragment = new AllPostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putBoolean(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getBoolean(ARG_PARAM2);
        }
        wpClientRetrofit = new WpClientRetrofit(getString(R.string.wp_base_url), "gulshan", "gulkush88+");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_posts, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rc_posts);
        pb = (ProgressBar)view.findViewById(R.id.pb);
        adapter = new PostsAdapter(posts, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {



                    visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                    if (loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            //Toast.makeText(activity, "Last Item Wow !", Toast.LENGTH_SHORT).show();
                            Log.d("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data

                            Toast.makeText(getActivity(), "Loading more videos..", Toast.LENGTH_SHORT).show();
                            page++;
                            fetchData();
                        }
                    }
                }
            }
        });
        fetchData();
        return view;
    }

    private void fetchData() {
        pb.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put("tutorials", mParam1);
        params.put("page", String.valueOf(page));
        //Toast.makeText(getActivity(), "Page: " + page, Toast.LENGTH_SHORT).show();
        wpClientRetrofit.getPosts(params, new WordPressRestResponse<List<Post>>() {
                    @Override
                    public void onSuccess(List<Post> result) {
                        Log.d("onSuccess.:", String.valueOf(result.size()));
                        loading = result.size() > 0 ? true : false;
                        //Toast.makeText(getActivity(), "Success" + String.valueOf(result.size()), Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        posts.addAll(result);
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onFailure(HttpServerErrorResponse errorResponse) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Error: "+errorResponse.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("onFailure:", errorResponse.toString());
                    }
                }, mParam2);
    }

}
