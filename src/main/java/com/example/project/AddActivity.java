package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project.databinding.ActivityAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    private  String title="",description="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               title=binding.title.getText().toString();
               description=binding.description.getText().toString();
               saveNote();
            }
        });

    }
 //id,title,description
    private void saveNote(){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("saving");
        progressDialog.setMessage("your note");
        progressDialog.show();
        String noteId= UUID.randomUUID().toString();
        NotesModel notesModel= new NotesModel(noteId,title,description,firebaseAuth.getUid());
        FirebaseFirestore  firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("notes")
        .document(noteId)
                .set(noteId)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Note Saved",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                  Toast.makeText(AddActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                  progressDialog.cancel();
                    }
                });
    }
}
