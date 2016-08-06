package com.contacts.firebase.firebasecontacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contacts> ContactList;
    private ListView listview;
    private ArrayAdapter adapter;
    private FirebaseDatabase friendsDatabase;
    private DatabaseReference friendsDataReference;
    private ChildEventListener ChildhoodFriends;

    private EditText ConName;
    private EditText ConNumber;

    private Button Add;
    private Button Delete;
    private Button Update;
    private Button Call;

    private ListViewCompat LVCcontacts;
    private ArrayList<Contacts> ALcontacts;
    Contacts contact = new Contacts();
    String FriendName,ContactNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        friendsDatabase = FirebaseDatabase.getInstance();
        friendsDataReference = friendsDatabase.getReference("");

        Add = (Button) findViewById(R.id.buttonAddItem);
        Delete = (Button) findViewById(R.id.buttonDeleteItem);
        Update = (Button) findViewById(R.id.buttonUpdateItem);
        Call = (Button) findViewById(R.id.buttonCallItem);

        ConName = (EditText) findViewById(R.id.txtName);
        ConNumber = (EditText) findViewById(R.id.txtNumber);

        LVCcontacts = (ListViewCompat) findViewById(R.id.list);

        friendsDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ALcontacts = new ArrayList<Contacts>();
                Iterable<DataSnapshot> snapshotIterable = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterable.iterator();

                while (iterator.hasNext()) {
                    Contacts conts = iterator.next().getValue(Contacts.class);
                    ALcontacts.add(conts);

                    ListAdapter adapter = new Adapter(MainActivity.this, R.layout.listview, ALcontacts);
                    LVCcontacts.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendName = ConName.getText().toString();
                ContactNumber = ConNumber.getText().toString();
                contact.setContactNumber(ContactNumber);
                contact.setFriendsName(FriendName);
                String key = friendsDataReference.push().getKey();
                Contacts contact = new Contacts(FriendName, ContactNumber);
                friendsDataReference.child(key).setValue(contact);
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteContact(contact);
            }
        });

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("No:" + ConNumber.getText().toString()));

                try{
                    startActivity(intent);
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Not enought load",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteContact(Contacts contacts){

        Query deleteQuery = friendsDataReference.orderByChild("FriendName").equalTo(contacts.getFriendsName());

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }
            //
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

