package men.ngopi.sans.news.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.news.MainActivity;
import men.ngopi.sans.news.NewsDetailActivity;
import men.ngopi.sans.news.R;
import men.ngopi.sans.news.models.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<NewsModel> data;

    public ArrayList<NewsModel> getData() {
        return data;
    }

    public void setData(ArrayList<NewsModel> data) {
        this.data = data;
        Log.d("update hrsnya", data.toString());
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel dt = this.data.get(position);
        String title = dt.getTitle();
        String description = dt.getDescription();

        Log.d("update ga", "nih");
        holder.titletTx.setText(title);
        holder.descriptionTxt.setText(description);
        holder.data = dt;
        holder.adapter = this;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titletTx;
        public TextView descriptionTxt;
        public NewsModel data;
        public NewsAdapter adapter;
        public LinearLayout newsLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titletTx = itemView.findViewById(R.id.title_txt);
            this.descriptionTxt = itemView.findViewById(R.id.description_txt);
            this.newsLinearLayout = itemView.findViewById(R.id.news_linear_layout);

            newsLinearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.news_linear_layout) {
                Intent detailNewsActivityIntent = new Intent(MainActivity.getInstance(), NewsDetailActivity.class);
                detailNewsActivityIntent.putExtra("title", data.getTitle());
                detailNewsActivityIntent.putExtra("description", data.getDescription());
                detailNewsActivityIntent.putExtra("url", data.getUrl());
                MainActivity.getInstance().startActivity(detailNewsActivityIntent);
            }
        }
    }
}
