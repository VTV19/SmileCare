package com.example.smilecare.ui.notes;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private DatabaseReference notesDatabaseReference;
    private MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public NotesViewModel(@NonNull Application application) {
        super(application);
        // Инициализация ссылки на базу данных Firebase
        notesDatabaseReference = FirebaseDatabase.getInstance().getReference("Notes");
    }

    public LiveData<List<Note>> getNotes() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            Log.d("NotesViewModel", "Fetching notes for userId: " + userId);

            // Слушаем изменения данных в Firebase для текущего пользователя
            notesDatabaseReference.orderByChild("userId").equalTo(userId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Note> notes = new ArrayList<>();
                            if (snapshot.exists()) {
                                for (DataSnapshot noteSnapshot : snapshot.getChildren()) {
                                    Note note = noteSnapshot.getValue(Note.class);
                                    if (note != null) {
                                        Log.d("NotesViewModel", "Note found: " + note.toString());
                                        notes.add(note);
                                    }
                                }
                                // Обновляем LiveData
                                notesLiveData.setValue(notes);
                            } else {
                                notesLiveData.setValue(notes);
                                Log.d("NotesViewModel", "No notes found for userId: " + userId);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("NotesViewModel", "Error loading notes: ", error.toException());
                        }
                    });
        } else {
            Log.d("NotesViewModel", "No authenticated user found");
        }
        return notesLiveData;
    }


    public void remove(Note note) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null && note.getId() != null) {
            // Удаление заметки из Firebase
            notesDatabaseReference.child(note.getId()).removeValue();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Firebase использует внутренние слушатели, очистка compositeDisposable не нужна
    }
}
