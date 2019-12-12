package com.example.lonkin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListsActivity extends AppCompatActivity {

    final String usr = "Paul";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("dialogs");

    ArrayList<String> dialogs = new ArrayList();
    ArrayAdapter<String> adapter;

    ArrayList<String> selectedDialogs = new ArrayList();
    ListView dialogsList;
    RecyclerView mMessagesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

    //    dialogs.add("Mark");

        Bundle arguments = getIntent().getExtras();
        final String user = arguments.get("username").toString();

        dialogsList = (ListView)findViewById(R.id.dialogL);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dialogs);
        dialogsList.setAdapter(adapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String dia = dataSnapshot.getValue(String.class);
                dialogs.add(dia);
               // adapter.notifyDataSetChanged();
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

        dialogsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем нажатый элемент
             //   EditText phoneEditText = (EditText) findViewById(R.id.phone);
       //       Bundle arguments = getIntent().getExtras();
       //       final String user = arguments.get("username").toString();
                Intent intent = new Intent(ListsActivity.this, MainActivity.class);
                String str = dialogs.get(position);
                intent.putExtra("dialogname", user + " " + str);
              //  intent.putExtra("username", user);
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

    }

    public void add(View view){
        EditText phoneEditText = (EditText) findViewById(R.id.phone);
        String dialog = phoneEditText.getText().toString();
        if(!dialog.isEmpty()){
            adapter.add(dialog);
            myRef.child(dialog).setValue(dialog);
          //  adapter.notifyDataSetChanged();

        }
    }

    public void remove(View view){
        for(int i=0; i< selectedDialogs.size();i++){
            adapter.remove(selectedDialogs.get(i));
        }
        // снимаем все ранее установленные отметки
        dialogsList.clearChoices();
        // очищаем массив выбраных объектов
        selectedDialogs.clear();

        adapter.notifyDataSetChanged();

    }
}
