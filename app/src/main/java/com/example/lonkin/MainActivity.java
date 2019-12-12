package com.example.lonkin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int MAX_MESSAGE_LENGTH = 150;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    EditText mEditTextMessage;
    Button mSendButton;
    RecyclerView mMessagesRecycler;

    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> _usr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arguments = getIntent().getExtras();
     //   Bundle arguments1 = getIntent().getExtras();

        final String dialogname = arguments.get("dialogname").toString();
        int c = 0;
        for(int i = 0; i < dialogname.length(); i++){
            if(dialogname.charAt(i) == ' ') {
                c = i;
                break;
            }
        }
        final String user = dialogname.substring(0, c);

        myRef = database.getReference("chats").child(dialogname.substring(c + 1, dialogname.length() + 0));


        mEditTextMessage = findViewById(R.id.message_input);
        mSendButton = findViewById(R.id.send_message_b);
        mMessagesRecycler = findViewById(R.id.messages_recycler);

        mMessagesRecycler.setLayoutManager(new LinearLayoutManager(this));

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = user + " " + mEditTextMessage.getText().toString() ;

                if(msg.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter message!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(msg.length() > MAX_MESSAGE_LENGTH){
                    Toast.makeText(getApplicationContext(), "Message Too Long!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue(msg);
                mEditTextMessage.setText("");

            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String msg = dataSnapshot.getValue(String.class);
                int l = 0;
                for(int i = 0; i < msg.length(); i++){
                    if(msg.charAt(i) == ' ') {
                        l = i;
                        break;
                    }
                }
                final String usr = msg.substring(0, l);
                final DataAdapter dataAdapter = new DataAdapter(MainActivity.this, messages, _usr);
                mMessagesRecycler.setAdapter(dataAdapter);
                messages.add(msg.substring(l + 1, msg.length() + 0));
                _usr.add(usr);
                //
                dataAdapter.notifyDataSetChanged();
                mMessagesRecycler.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity.this, ListsActivity.class);
        startActivity(intent);
    }
}
