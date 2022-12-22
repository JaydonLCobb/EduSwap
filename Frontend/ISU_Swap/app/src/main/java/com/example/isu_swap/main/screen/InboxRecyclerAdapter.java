/**
 * Class representing the RecyclerView adapter unique to the Homepage screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isu_swap.main.model.Message;

import java.util.ArrayList;

public class InboxRecyclerAdapter extends RecyclerView.Adapter<InboxRecyclerAdapter.ViewHolder> {
    private final ArrayList<Message> messageList;

    public InboxRecyclerAdapter(ArrayList<Message> messageList)
    {
        this.messageList = messageList;
    }

    /**
     * Class which represents the ViewHolder necessary for RecyclerView functionality
     */
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

        /**
         * @return This layout's body TextView
         */
        public TextView getMessageTextView() {
            return message;
        }

        /**
         * @return This layout's subject header TextView
         */
        public TextView getSubjectTextView() {
            return subject;
        }

        /**
         * @return This layout's sender username TextView
         */
        public TextView getSenderTextView() {
            return sender;
        }
    }

    /**
     * Inflates the ViewHolder for this RecyclerView
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public InboxRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Sets XML elements for every item in the RecyclerView
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InboxRecyclerAdapter.ViewHolder holder, int position) {
        holder.getMessageTextView().setText(messageList.get(position).getMessage());
        holder.getSubjectTextView().setText(messageList.get(position).getSubject());
        holder.getSenderTextView().setText(String.valueOf(messageList.get(position).getSender()));
    }

    /**
     * @return size of this RecyclerView's items
     */
    @Override
    public int getItemCount() {
        if (messageList != null) {
            return messageList.size();
        }
        else
        {
            return 0;
        }
    }
}
