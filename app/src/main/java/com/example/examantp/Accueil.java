package com.example.examantp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {

    private TextView tvusername;
    private Button btnajout, btnaff, btndeconnecter;

    // Liste statique partagée des contacts
    public static ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        tvusername = findViewById(R.id.tvuser_acc);
        btnajout = findViewById(R.id.btn_ajout_acc);
        btnaff = findViewById(R.id.btn_afficher_acc);
        btndeconnecter = findViewById(R.id.btn_deconnecter);

        // Obtenir le nom de l'utilisateur
        Intent x = this.getIntent();
        String u = x.getStringExtra("USER");
        tvusername.setText("Accueil de " + u);

        // Ajouter un clic pour le bouton "Ajouter"
        btnajout.setOnClickListener(view -> {
            Intent i = new Intent(Accueil.this, Ajout.class);
            startActivity(i);
        });

        // Ajouter un clic pour le bouton "Afficher"
        btnaff.setOnClickListener(view -> {
            Intent i = new Intent(Accueil.this, Affichage.class);
            startActivity(i);
        });

        // Ajouter un clic pour le bouton "Déconnecter"
        btndeconnecter.setOnClickListener(view -> {
            // Effacer les préférences utilisateur
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Revenir à l'écran de connexion
            Intent i = new Intent(Accueil.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    // Ajouter un contact à la liste statique
    public static void addContact(Contact contact) {
        contacts.add(contact);
    }

    // Obtenir la liste des contacts
    public static ArrayList<Contact> getContacts() {
        return contacts;
    }
}
