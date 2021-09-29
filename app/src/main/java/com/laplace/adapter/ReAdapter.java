package com.laplace.adapter;

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

    private List<Chat> message;
    public String key;

    public void setMessage(List<Chat> message) {
        this.message = message;
    }

    public ReAdapter(List<Chat> message) {
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
        holder.textView.setText(AHelper.toContent("", message.get(position).getMsg()));
    }


    @Override
    public int getItemCount() {
        return message == null ? 0 : message.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }

}
