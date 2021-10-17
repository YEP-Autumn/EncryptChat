package com.laplace.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laplace.encryptchat.R;
import com.laplace.bean.utilsbean.M;

import java.util.ArrayList;
import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {

    private final String TAG = "YEP";
    public List<M> message = new ArrayList<>();

    public ReAdapter() {

    }


    public List<M> getMessage() {
        return message;
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
        if (m.isMe()) {
            holder.leftLayoutRight.setVisibility(View.VISIBLE);//显示右边消息布局
            holder.leftLayoutLeft.setVisibility(View.GONE);//隐藏左边消息布局
            holder.textViewRight.setText(m.getMessage());//将消息内容显示
        } else {
            holder.leftLayoutLeft.setVisibility(View.VISIBLE);
            holder.leftLayoutRight.setVisibility(View.GONE);
            holder.textViewLeft.setText(m.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return message == null ? 0 : message.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewLeft;

        public TextView textViewRight;

        public LinearLayout leftLayoutLeft;

        public LinearLayout leftLayoutRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLeft = itemView.findViewById(R.id.text_view_left);
            textViewRight = itemView.findViewById(R.id.text_view_right);
            leftLayoutLeft = (LinearLayout) itemView.findViewById(R.id.linearlayout_left);
            leftLayoutRight = (LinearLayout) itemView.findViewById(R.id.linearlayout_right);
            // 用于适配低版本
            textViewLeft.setTextIsSelectable(true);
            textViewRight.setTextIsSelectable(true);
            textViewLeft.setOnLongClickListener(view -> {
                ClipboardManager cmb = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                TextView tv = (TextView) view;
                ClipData clipData = ClipData.newPlainText("copy", tv.getText());
                cmb.setPrimaryClip(clipData);
                Toast.makeText(view.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                return true;
            });
            textViewRight.setOnLongClickListener(view -> {
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
