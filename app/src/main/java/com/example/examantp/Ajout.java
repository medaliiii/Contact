package com.example.examantp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ajout extends AppCompatActivity {

    private Button btnsauvegard;
    private Button btnquitter;
    EditText ednom, edpseudo, ednum;
    Spinner spinnerCountryCode;
    ContactManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        // Initialisation des vues
        ednom = findViewById(R.id.ednom_ajout);
        edpseudo = findViewById(R.id.edpseudo_ajout);
        ednum = findViewById(R.id.ednum_ajout);
        spinnerCountryCode = findViewById(R.id.spinner_country_code);
        btnquitter = findViewById(R.id.btnquitter_ajout);
        btnsauvegard = findViewById(R.id.btnsauvgarder_ajout);

        // Initialisation du ContactManager (pas besoin de base de données ici)
        manager = new ContactManager();

        btnquitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajout.this.finish(); // Fermer l'activité
            }
        });

        btnsauvegard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = ednom.getText().toString();
                String pseudo = edpseudo.getText().toString();
                String num = ednum.getText().toString();

                // Récupérer l'indicatif téléphonique sélectionné dans le Spinner
                String countryCode = spinnerCountryCode.getSelectedItem().toString().trim();

                // Concaténer le code de pays et le numéro
                String fullNumber = countryCode + num; // countryCode doit déjà contenir le '+'

                // Création du contact
                Contact c = new Contact(nom, pseudo, fullNumber);

                // Ajout du contact dans la liste statique
                long result = manager.ajout(nom, pseudo, fullNumber);

                if (result != -1) {
                    // Succès de l'ajout
                    Toast.makeText(Ajout.this, "Contact ajouté: " + c.toString(), Toast.LENGTH_SHORT).show();

                    // Vider les champs après la sauvegarde
                    ednom.setText("");
                    edpseudo.setText("");
                    ednum.setText("");
                } else {
                    // Échec de l'ajout
                    Toast.makeText(Ajout.this, "Échec de l'ajout du contact", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
