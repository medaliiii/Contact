package com.example.examantp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class Affichage extends AppCompatActivity {

    RecyclerView rv;
    MyContactRecyclerAdapter adapter;
    List<Contact> originalContacts;
    ArrayList<Contact> filteredContacts;
    private String phoneNumberToCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        rv = findViewById(R.id.rvaffiche);

        // Initialiser les contacts à partir de la liste statique
        originalContacts = ContactManager.getAllContact();
        filteredContacts = new ArrayList<>(originalContacts);

        adapter = new MyContactRecyclerAdapter(Affichage.this, filteredContacts);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(Affichage.this, LinearLayoutManager.VERTICAL, false));

        // Initialiser la SearchView
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        filteredContacts.clear();

        if (text.isEmpty()) {
            filteredContacts.addAll(originalContacts);
        } else {
            for (Contact contact : originalContacts) {
                if (contact.getNom().toLowerCase().contains(text.toLowerCase()) ||
                        contact.getNum().contains(text)) {
                    filteredContacts.add(contact);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void makePhoneCall(String phoneNumber) {
        phoneNumberToCall = phoneNumber;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Affichage", "Permission CALL_PHONE non accordée, demande en cours.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Log.d("Affichage", "Permission CALL_PHONE déjà accordée, appel en cours.");
            startDialIntent(phoneNumber);
        }
    }

    private void startDialIntent(String phoneNumber) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:" + phoneNumber));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(i);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Affichage", "Permission CALL_PHONE accordée.");
                if (phoneNumberToCall != null) {
                    startDialIntent(phoneNumberToCall);
                }
            } else {
                Log.d("Affichage", "Permission CALL_PHONE refusée.");
            }
        }
    }
}