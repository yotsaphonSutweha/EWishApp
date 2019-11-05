package nci.yo.ewishapp;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import nci.yo.ewishapp.DataObjects.WishItem;

import static android.content.ContentValues.TAG;

public class AddFragment extends Fragment {
    private String itemName = "";
    private String amount = "";
    private String price = "";
    private String event = "";
    private String receiver = "";
    private boolean bought = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FirebaseFirestore db =  FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        final EditText itemEditText = view.findViewById(R.id.itemEditText);
        final EditText amountEditText = view.findViewById(R.id.amountEditText);
        final EditText priceEditText = view.findViewById(R.id.priceEditText);
        final EditText eventEditText = view.findViewById(R.id.eventEditText);
        final EditText receiverEditText = view.findViewById(R.id.receiverEditText);
        final CheckBox boughtCheckBox = view.findViewById(R.id.boughtCheckBox);
        final Button addBtn = view.findViewById(R.id.addButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemName = itemEditText.getText().toString();
                amount = amountEditText.getText().toString();
                price = priceEditText.getText().toString();
                event = eventEditText.getText().toString();
                receiver = receiverEditText.getText().toString();
                if(boughtCheckBox.isChecked()) {
                    bought = true;
                } else {
                    bought = false;
                }
                final WishItem item = new WishItem();
                item.setItem(itemName);
                item.setAmount(Integer.parseInt(amount));
                item.setPrice(Integer.parseInt(price));
                item.setEvent(event);
                item.setReceiver(receiver);
                item.setBought(bought);

                DocumentReference docRef = db.collection("wishes").document();
                docRef.set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Wish Item is added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to add Wish Item", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
