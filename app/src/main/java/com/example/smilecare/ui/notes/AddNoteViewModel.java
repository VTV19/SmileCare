package com.example.smilecare.ui.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;

public class AddNoteViewModel extends AndroidViewModel {

    private DatabaseReference notesDatabaseReference;

    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        // Инициализация ссылки на базу данных Firebase
        notesDatabaseReference = FirebaseDatabase.getInstance().getReference("Notes");
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void saveNote(Note note) {
        // Генерация уникального идентификатора для каждой заметки
        String noteId = notesDatabaseReference.push().getKey();
        note.setId(noteId);
        if (noteId != null) {
            // Сохранение заметки в Firebase
            notesDatabaseReference.child(noteId).setValue(note, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        // Если сохранение успешно, закрыть экран
                        shouldCloseScreen.setValue(true);
                    } else {
                        // Обработка ошибки (например, можно вывести сообщение)
                        shouldCloseScreen.setValue(false);
                    }
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Нет необходимости очищать подписки, так как Firebase использует асинхронные события
    }
}
