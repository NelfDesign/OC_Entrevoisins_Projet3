package com.openclassrooms.entrevoisins.ui.neighbour_list.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    //interface to get the click on the item
    public interface onItemListener{
        void onItemClick(int position);
    }

    //Fields
    private final List<Neighbour> mNeighbours;
    private onItemListener mOnItemListener;

    /**
     * Add listener to the constructor
     * @param items
     * @param onItemListener listener to listen the click
     */
    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, onItemListener onItemListener) {
        mNeighbours = items;
        this.mOnItemListener = onItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        //return a new ViewHolder with the view and the listener
        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //position of the neighbour in the list
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        //check if the neighbour is a favorite or not to delete it to the right list
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!neighbour.isFavorite()){
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                }else{
                    EventBus.getDefault().post(new DeleteFavoriteEvent(neighbour));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    /**
     * add the listener to listen the click to the item
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        onItemListener onItemListener;

        public ViewHolder(View view, onItemListener onItemListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.onItemListener = onItemListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
