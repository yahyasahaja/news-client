package men.ngopi.sans.news.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.news.MainActivity;
import men.ngopi.sans.news.R;
import men.ngopi.sans.news.adapters.FeedsAdapter;
import men.ngopi.sans.news.adapters.NewsAdapter;
import men.ngopi.sans.news.models.FeedsModel;
import men.ngopi.sans.news.models.NewsModel;

/**
 * Created by yahyasahaja on 17/12/2018.
 */

public class FeedsFragment extends Fragment implements TextWatcher {

    RecyclerView feedsCard;
    ArrayList<FeedsModel> feeds = new ArrayList<>();
    FeedsAdapter feedsAdapter;
    private View view;
    ProgressBar progressBar;
    MaterialSearchBar searchBar;
    String searchQuery = "";

    public static FeedsFragment newInstance(){
        return new FeedsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feeds, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        feedsAdapter = new FeedsAdapter();
        feedsCard = view.findViewById(R.id.feeds_recycler_view);
        feedsCard.setLayoutManager(new LinearLayoutManager(MainActivity.getInstance()));
        progressBar = view.findViewById(R.id.progress_bar);
        searchBar = view.findViewById(R.id.feeds_search_bar);

        AndroidNetworking.initialize(MainActivity.getInstance());
        this.fetchAllFeeds();
        searchBar.addTextChangeListener(this);
    }

    public void fetchAllFeeds() {
        Log.d("ini ga kepanggil 2", "hmm");
        final ArrayList<FeedsModel> feeds = this.feeds = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        feedsCard.setVisibility(View.GONE);

        String search = "";

        if (searchQuery.length() >= 0) search = "?search=" + searchQuery;

        AndroidNetworking.get("http://192.168.0.145:8000/feeds" + search)
                .setTag("feeds")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                                     @Override
                                     public void onResponse(JSONArray response) {
                                         progressBar.setVisibility(View.GONE);
                                         feedsCard.setVisibility(View.VISIBLE);
                                         Log.d("ini ga kepanggil", "hmm");
                                         try {
                                             for (int i = 0 ; i < response.length(); i++) {
                                                 JSONObject article = response.getJSONObject(i);
                                                 String text = article.getString("text");
                                                 String name = article.getJSONObject("user").getString("name");
                                                 FeedsModel feedsModel = new FeedsModel(name, text);
                                                 feeds.add(feedsModel);
                                             }

                                             feedsAdapter.setData(feeds);
                                             feedsCard.setAdapter(feedsAdapter);

                                             Log.d("coba lg", feeds.toString());
                                         } catch (JSONException err) {
                                             err.printStackTrace();
                                         }
                                     }
                                     @Override
                                     public void onError(ANError error) {
                                         // handle error
                                     }
                                 }
                );
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchQuery = s.toString();
        fetchAllFeeds();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
