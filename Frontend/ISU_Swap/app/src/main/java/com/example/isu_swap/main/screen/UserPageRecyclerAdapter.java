/**
 * Class representing the RecyclerView adapter unique to the Profile screen
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.screen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.isu_swap.main.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserPageRecyclerAdapter extends RecyclerView.Adapter<UserPageRecyclerAdapter.ViewHolder> {
    final long ONE_MEGABYTE = 1024 * 1024;
    private final ArrayList<Item> itemList;
    private final ItemListener itemListener2;
    private final StorageReference storageRef;

    public UserPageRecyclerAdapter(ArrayList<Item> itemList, ItemListener itemListener) {
        this.itemList = itemList;
        itemListener2 = itemListener;
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    /**
     * Class which represents the ViewHolder necessary for RecyclerView functionality
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView title;
        private final TextView price;
        private final ImageView preview;
        private final TextView seller;
        private final CardView itemCardView;
        private final ItemListener itemListener;

        public ViewHolder(@NonNull View itemView, ItemListener itemListener) {
            super(itemView);
            title = itemView.findViewById(R.id.item_card_name_edit);
            price = itemView.findViewById(R.id.item_card_price_edit);
            preview = itemView.findViewById(R.id.item_card_preview_image_edit);
            seller = itemView.findViewById(R.id.item_card_seller_edit);
            itemCardView = itemView.findViewById(R.id.item_card_edit_button);
            this.itemListener = itemListener;

            itemView.setOnClickListener(this);
        }

        /**
         * @return This layout's title TextView
         */
        public TextView getTitleTextView() {return title;}

        /**
         * @return This layout's price TextView
         */
        public TextView getPriceTextView() {return price;}

        /**
         * @return This layout's preview image ImageView
         */
        public ImageView getPreviewImageView() {return preview;}

        /**
         * @return This layout's seller username TextView
         */
        public TextView getSellerTextView() {return seller;}

        /**
         * @return this layout's item CardView
         */
        public CardView getItemCardView() {return itemCardView;}

        /**
         * Sets the onClick for every item in the RecyclerView
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            itemListener.onItemClick(getAdapterPosition());
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout_user, parent, false);
        return new UserPageRecyclerAdapter.ViewHolder(view, itemListener2);
    }

    /**
     * Sets XML elements for every item in the RecyclerView
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleTextView().setText(itemList.get(position).getTitle());
        holder.getPriceTextView().setText("$" + itemList.get(position).getPrice());
        holder.getSellerTextView().setText(itemList.get(position).getLister().getUsername());

        StorageReference pathRef = storageRef.child("images/" + itemList.get(position).getImage());
        pathRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.getPreviewImageView().setImageBitmap(Bitmap.createScaledBitmap(bm, holder.getPreviewImageView().getWidth(), holder.getPreviewImageView().getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @return size of this RecyclerView's items
     */
    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        } else {return 0;}
    }

    /**
     * Interface representing the onClick for every item in this RecyclerView
     */
    public interface ItemListener {
        void onItemClick(int position);
    }
}
