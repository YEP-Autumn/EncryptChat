package com.laplace.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laplace.encryptchat.R;
import com.laplace.bean.utilsbean.M;

import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {

    private final String TAG = "YEP";
    private List<M> message;

    public ReAdapter() {

    }

    public ReAdapter(List<M> message) {
        this.message = message;
    }

    public List<M> getMessage() {
        return message;
    }

    public void setMessage(List<M> message) {
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
        M m = message.get(position);
//        if (m.isMe()) {
//            holder.textView.setBackgroundResource(R.drawable.shape_reverse);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.gravity = Gravity.RIGHT;
//            lp.rightMargin = 30;
//            lp.topMargin = 80;
//            lp.leftMargin = 30;
//            lp.bottomMargin = 80;
//            holder.textView.setLayoutParams(lp);
//        } else {
//            holder.textView.setBackgroundResource(R.drawable.shape);
//        }
        holder.textView.setText(m.getMessage());
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
            // 用于适配低版本
            textView.setTextIsSelectable(true);
            textView.setOnLongClickListener(view -> {
                ClipboardManager cmb = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                TextView tv = (TextView) view;
                ClipData clipData = ClipData.newPlainText("copy", tv.getText());
                cmb.setPrimaryClip(clipData);
                Toast.makeText(view.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }

}
