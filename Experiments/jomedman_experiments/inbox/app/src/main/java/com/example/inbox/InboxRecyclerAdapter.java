package com.example.inbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inbox.model.Post;
import java.util.ArrayList;

public class InboxRecyclerAdapter extends RecyclerView.Adapter<InboxRecyclerAdapter.ViewHolder> {
    private ArrayList<Post> postList;

    public InboxRecyclerAdapter(ArrayList<Post> postList)
    {
        this.postList = postList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView subject;
        private final TextView sender;

        public ViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.message_text);
            subject = view.findViewById(R.id.subject_text);
            sender = view.findViewById(R.id.sender_text);
        }

        public TextView getMessageTextView() {
            return message;
        }

        public TextView getSubjectTextView() {
            return subject;
        }

        public TextView getSenderTextView() {
            return sender;
        }
    }

    @NonNull
    @Override
    public InboxRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxRecyclerAdapter.ViewHolder holder, int position) {
        holder.getMessageTextView().setText(postList.get(position).getBody());
        holder.getSubjectTextView().setText(postList.get(position).getTitle());
        holder.getSenderTextView().setText(String.valueOf(postList.get(position).getUserId()));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
