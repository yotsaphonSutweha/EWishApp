package nci.yo.ewishapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import nci.yo.ewishapp.DataObjects.WishItem;

public class ItemAdapter extends FirestoreRecyclerAdapter<WishItem, ItemAdapter.ItemHolder> {
    private OnItemClickListener listener;

    public ItemAdapter(@NonNull FirestoreRecyclerOptions<WishItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull WishItem model) {
        holder.textViewItemName.setText(model.getItem());
        holder.textViewAmount.setText(Integer.toString(model.getAmount()));
        holder.textViewPrice.setText(Integer.toString(model.getPrice()));
        holder.textViewEvent.setText(model.getEvent());
        holder.textViewReceiver.setText(model.getReceiver());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ItemHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName;
        TextView textViewAmount;
        TextView textViewPrice;
        TextView textViewEvent;
        TextView textViewReceiver;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.text_view_item);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            textViewReceiver = itemView.findViewById(R.id.text_view_receiver);
            textViewEvent = itemView.findViewById(R.id.text_view_event);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface  OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
