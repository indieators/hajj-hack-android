package com.nloops.hackathon.adapters;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nloops.hackathon.R;
import com.nloops.hackathon.adapters.ElementsAdapter.ElementViewHolder;
import com.nloops.hackathon.models.CardModel;

public class ElementsAdapter extends RecyclerView.Adapter<ElementViewHolder> {

  private Cursor mCursor;
  private OnCardItemClicked mClickListener;

  public ElementsAdapter(Cursor cursor, OnCardItemClicked callBacks) {
    this.mCursor = cursor;
    this.mClickListener = callBacks;
  }

  private void postItemClick(ElementViewHolder holder) {
    if (mClickListener != null) {
      mClickListener.onItemClick(holder.getAdapterPosition());
    }
  }

  @NonNull
  @Override
  public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    int layoutID = R.layout.elements_card_item;
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(layoutID, parent, false);
    return new ElementViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
    CardModel currentCard = getItem(position);
    holder.mTitle.setText(currentCard.getTitle());
    holder.mStatus.setText(currentCard.getStatus());
    holder.mElementIcon.setImageResource(currentCard.getIcon());
  }

  /**
   * Helper Method that retrieve a {@link CardModel} from Cursor using Position.
   */
  public CardModel getItem(int position) {
    if (!mCursor.moveToPosition(position)) {
      throw new IllegalStateException("Invalid item position requested");
    }
    return new CardModel(mCursor);
  }

  public void swapCursor(Cursor cursor) {
    mCursor = cursor;
    notifyDataSetChanged();
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).getID();
  }

  @Override
  public int getItemCount() {
    return (mCursor != null) ? mCursor.getCount() : 0;
  }

  public interface OnCardItemClicked {

    void onItemClick(int position);
  }

  class ElementViewHolder extends RecyclerView.ViewHolder implements
      View.OnClickListener {

    @BindView(R.id.iv_element_icon)
    ImageView mElementIcon;
    @BindView(R.id.tv_element_title)
    TextView mTitle;
    @BindView(R.id.tv_element_status)
    TextView mStatus;

    public ElementViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      postItemClick(this);
    }
  }

}
