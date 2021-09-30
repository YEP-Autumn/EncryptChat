package com.laplace.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laplace.encryptUtils.AHelper;
import com.laplace.encryptchat.R;
import com.laplace.intermediator.Chat;

import org.w3c.dom.Text;

import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {

    private final String TAG = "YEP";
    private List<String> message;

    public ReAdapter() {
    }

    public ReAdapter(List<String> message) {
        this.message = message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (message == null) return;
        holder.textView.setText(message.get(position));
    }


    @Override
    public int getItemCount() {
        return message == null ? 0 : message.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }

}
