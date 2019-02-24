package men.ngopi.sans.news.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import men.ngopi.sans.news.MainActivity;
import men.ngopi.sans.news.NewsDetailActivity;
import men.ngopi.sans.news.R;
import men.ngopi.sans.news.models.FeedsModel;
import men.ngopi.sans.news.models.NewsModel;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    ArrayList<FeedsModel> data;

    public ArrayList<FeedsModel> getData() {
        return data;
    }

    public void setData(ArrayList<FeedsModel> data) {
        this.data = data;
        Log.d("update hrsnya", data.toString());
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feeds_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedsModel dt = this.data.get(position);
        String name = dt.getName();
        String text = dt.getText();

        Log.d("update ga", "nih");
        holder.nameTxt.setText(name);
        holder.textTxt.setText(text);
        holder.data = dt;
        holder.adapter = this;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTxt;
        public TextView textTxt;
        public FeedsModel data;
        public FeedsAdapter adapter;
        public LinearLayout newsLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTxt = itemView.findViewById(R.id.name_txt);
            this.textTxt = itemView.findViewById(R.id.text_txt);
            this.newsLinearLayout = itemView.findViewById(R.id.news_linear_layout);

            newsLinearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.news_linear_layout) {
//                Intent detailNewsActivityIntent = new Intent(MainActivity.getInstance(), NewsDetailActivity.class);
//                detailNewsActivityIntent.putExtra("title", data.getTitle());
//                detailNewsActivityIntent.putExtra("description", data.getDescription());
//                detailNewsActivityIntent.putExtra("url", data.getUrl());
//                MainActivity.getInstance().startActivity(detailNewsActivityIntent);
            }
        }
    }
}
