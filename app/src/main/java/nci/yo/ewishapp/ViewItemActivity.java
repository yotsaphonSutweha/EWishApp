package nci.yo.ewishapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import nci.yo.ewishapp.DataObjects.WishItem;
import nci.yo.ewishapp.Helper.LocalHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ViewItemActivity extends AppCompatActivity {
    private String itemName, newItemName = "";
    private String amount, newAmount = "";
    private String price, newPrice = "";
    private String event, newEvent = "";
    private String receiver, newReceiver = "";
    private String bought;
    private boolean newBought;
    private WishItem editedItem;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        final EditText itemEditText = findViewById(R.id.itemEditText);
        final EditText amountEditText = findViewById(R.id.amountEditText);
        final EditText priceEditText = findViewById(R.id.priceEditText);
        final EditText eventEditText = findViewById(R.id.eventEditText);
        final EditText receiverEditText = findViewById(R.id.receiverEditText);
        final CheckBox boughtCheckBox = findViewById(R.id.boughtCheckBox);
        final Button editBtn = findViewById(R.id.editButton);
        final Button checkBtn = findViewById(R.id.checkBtn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String docPath = extras.getString("DocID");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference itemRef = db.document(docPath);
        itemRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                amount = documentSnapshot.getData().get("amount").toString();
                bought = documentSnapshot.getData().get("bought").toString();
                event = documentSnapshot.getData().get("event").toString();
                itemName = documentSnapshot.getData().get("item").toString();
                price = documentSnapshot.getData().get("price").toString();
                receiver = documentSnapshot.getData().get("receiver").toString();
                amountEditText.setText(amount);
                priceEditText.setText(price);
                eventEditText.setText(event);
                receiverEditText.setText(receiver);
                if (bought.equalsIgnoreCase("true")) {
                    boughtCheckBox.setChecked(true);
                } else if (bought.equalsIgnoreCase("true")) {
                    boughtCheckBox.setChecked(false);
                }
                itemEditText.setText(itemName);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemName = itemEditText.getText().toString();
                newAmount = amountEditText.getText().toString();
                newPrice = priceEditText.getText().toString();
                newEvent = eventEditText.getText().toString();
                newReceiver = receiverEditText.getText().toString();
                if (boughtCheckBox.isChecked()) {
                    newBought = true;
                } else {
                    newBought = false;
                }
                editedItem = new WishItem();
                editedItem.setItem(newItemName);
                editedItem.setAmount(Integer.parseInt(newAmount));
                editedItem.setPrice(Integer.parseInt(newPrice));
                editedItem.setEvent(newEvent);
                editedItem.setReceiver(newReceiver);
                editedItem.setBought(newBought);
                itemRef.set(editedItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ViewItemActivity.this, "Wish Item is edited", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(ViewItemActivity.this, MainActivity.class);
                            startActivity(intent1);
                        }
                        else {
                            Toast.makeText(ViewItemActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ViewItemActivity.this, AvailableItemsActivity.class);
                intent1.putExtra("SearchItem", itemName);
                startActivity(intent1);
            }
        });


    }
}
