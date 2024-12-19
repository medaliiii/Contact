package com.example.examantp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactRecyclerAdapter extends RecyclerView.Adapter<MyContactRecyclerAdapter.MyViewHolder> {
    Context con;
    ArrayList<Contact> data;

    public MyContactRecyclerAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_contact, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact c = data.get(position);
        holder.tvnom.setText(c.getNom());
        holder.tvprenom.setText(c.getPseudo());
        holder.tvnum.setText(c.getNum());

        holder.ivcall.setOnClickListener(view -> {
            if (con instanceof Affichage) {
                ((Affichage) con).makePhoneCall(c.getNum());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnom, tvnum, tvprenom;
        ImageView ivcall, ivupdate, ivdelete;

        public MyViewHolder(@NonNull View v) {
            super(v);
            tvnom = v.findViewById(R.id.tvnom_contact);
            tvprenom = v.findViewById(R.id.tvprenom_contact);
            tvnum = v.findViewById(R.id.tvnum_contact);
            ivcall = v.findViewById(R.id.imageviewcall_contact);
            ivupdate = v.findViewById(R.id.imageviewupdate_contact);
            ivdelete = v.findViewById(R.id.imageviewdelete_contact);

            // Deletion logic
            ivdelete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this contact?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    int position = getAdapterPosition();
                    Contact contactToDelete = data.get(position);
                    ContactManager.supprimerContact(contactToDelete.getId()); // Remove from static list
                    data.remove(position); // Remove from UI list
                    notifyItemRemoved(position); // Notify adapter
                    Toast.makeText(con, "Contact deleted", Toast.LENGTH_SHORT).show();
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            });

            // Update logic
            ivupdate.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Contact contactToUpdate = data.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                LayoutInflater inflater = LayoutInflater.from(con);
                View dialogView = inflater.inflate(R.layout.view_dialogue, null);

                EditText editName = dialogView.findViewById(R.id.et_nom);
                EditText editPseudo = dialogView.findViewById(R.id.et_pseudo);
                EditText editNumber = dialogView.findViewById(R.id.et_num);
                Button btnCancel = dialogView.findViewById(R.id.button);
                Button btnSave = dialogView.findViewById(R.id.btn_save);

                // Populate EditTexts with current data
                editName.setText(contactToUpdate.getNom());
                editPseudo.setText(contactToUpdate.getPseudo());
                editNumber.setText(contactToUpdate.getNum());

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // Cancel button
                btnCancel.setOnClickListener(v1 -> dialog.dismiss());

                // Save button
                btnSave.setOnClickListener(v1 -> {
                    String newName = editName.getText().toString().trim();
                    String newPseudo = editPseudo.getText().toString().trim();
                    String newNumber = editNumber.getText().toString().trim();

                    if (!newName.isEmpty() && !newPseudo.isEmpty() && !newNumber.isEmpty()) {
                        // Update contact in static list
                        ContactManager.modifierContact(contactToUpdate.getId(), newName, newPseudo, newNumber);
                        contactToUpdate.setNom(newName);
                        contactToUpdate.setPseudo(newPseudo);
                        contactToUpdate.setNum(newNumber);
                        notifyItemChanged(position);
                        Toast.makeText(con, "Contact updated", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(con, "All fields are required", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            });
        }
    }
}