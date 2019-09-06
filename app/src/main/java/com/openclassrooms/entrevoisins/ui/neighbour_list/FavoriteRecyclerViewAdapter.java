package com.openclassrooms.entrevoisins.ui.neighbour_list;

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
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nelfdesign at 29/08/2019
 * com.openclassrooms.entrevoisins.ui.neighbour_list
 */
public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder> {

    //Fields
    private List<Neighbour> favoriteNeighbours;
    private MyNeighbourRecyclerViewAdapter.onItemListener mOnItemListener;

    //constructor
    public FavoriteRecyclerViewAdapter(List<Neighbour> favoriteNeighbours,
                                       MyNeighbourRecyclerViewAdapter.onItemListener onItemListener) {
        this.favoriteNeighbours = favoriteNeighbours;
        this.mOnItemListener = onItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite, parent, false);
        return new ViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

       for (Neighbour favorite : favoriteNeighbours){
            favorite = favoriteNeighbours.get(position);
            if (favorite.isFavorite()){
                holder.favoriteName.setText(favorite.getName());
                Glide.with(holder.favoriteAvatar.getContext())
                        .load(favorite.getAvatarUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(holder.favoriteAvatar);

                Neighbour finalFavorite = favorite;
                holder.mDeleteButton.setOnClickListener(v -> {
                    EventBus.getDefault().post(new DeleteFavoriteEvent(finalFavorite));
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return favoriteNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.avatar)
        public ImageView favoriteAvatar;
        @BindView(R.id.item_list_name)
        public TextView favoriteName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        MyNeighbourRecyclerViewAdapter.onItemListener onItemListener;

        public ViewHolder(View view, MyNeighbourRecyclerViewAdapter.onItemListener onItemListener) {
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
