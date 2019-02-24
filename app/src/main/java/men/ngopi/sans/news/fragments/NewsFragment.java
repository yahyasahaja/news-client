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
import men.ngopi.sans.news.adapters.NewsAdapter;
import men.ngopi.sans.news.models.NewsModel;

/**
 * Created by yahyasahaja on 17/12/2018.
 */

public class NewsFragment extends Fragment implements TextWatcher {

    RecyclerView newsCard;
    ArrayList<NewsModel> news = new ArrayList<>();
    NewsAdapter newsAdapter;
    ProgressBar progressBar;
    private View view;
    MaterialSearchBar searchBar;
    String searchQuery = "bitcoin";

    public static NewsFragment newInstance(){
        NewsFragment fragmentHome = new NewsFragment();
        return fragmentHome;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newsAdapter = new NewsAdapter();
        newsCard = view.findViewById(R.id.news_recycler_view);
        newsCard.setLayoutManager(new LinearLayoutManager(MainActivity.getInstance()));
        progressBar = view.findViewById(R.id.progress_bar);
        searchBar = view.findViewById(R.id.news_search_bar);

        AndroidNetworking.initialize(MainActivity.getInstance());
        this.fetchAllNews();
        searchBar.addTextChangeListener(this);
    }

    public void fetchAllNews() {
        final ArrayList<NewsModel> news = this.news = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        newsCard.setVisibility(View.GONE);

        String search = "bitcoin";

        if (searchQuery.length() >= 2) search = searchQuery;

        Log.d("Stringnya", search + " " + searchQuery);
        AndroidNetworking.get("https://newsapi.org/v2/everything?q=" + search + "&from=2019-01-24&sortBy=publishedAt&apiKey=e4f18bdfb7a8410badef839a4739fee8")
                .setTag("news")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                                     @Override
                                     public void onResponse(JSONObject response) {
                                         progressBar.setVisibility(View.GONE);
                                         newsCard.setVisibility(View.VISIBLE);
                                         try {
                                             JSONArray articles = response.getJSONArray("articles");

                                             for (int i = 0 ; i < articles.length(); i++) {
                                                 JSONObject article = articles.getJSONObject(i);
                                                 String title = article.getString("title");
                                                 String description = article.getString("description");
                                                 String url = article.getString("url");
                                                 NewsModel newsModel = new NewsModel(title, description, url);
                                                 news.add(newsModel);
                                             }

                                             newsAdapter.setData(news);
                                             newsCard.setAdapter(newsAdapter);

                                             Log.d("coba lg", news.toString());
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
        fetchAllNews();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
