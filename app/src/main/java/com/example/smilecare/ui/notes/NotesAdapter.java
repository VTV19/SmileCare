package com.example.smilecare.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smilecare.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    //должна лежать коллекция объектов, которые нужно отобразить
    private List<Note> notes = new ArrayList<>();

    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    // сеттер - нужна возможность снаружи вставлять в адаптер новую коллекцию
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        // обновление данных
        notifyDataSetChanged();
    }

    // создавать View из макета (вызывается 10-12 раз)
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // получение экземпляра класса LayoutInflater
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    // как устанавливать значения во View
    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {
        // получаем элемент, который надо отобразить, по номеру позиции
        Note note = notes.get(position);

        viewHolder.textViewNote.setText(note.getText());

        int colorResId;
        switch (note.getPriority()) {
            case 0:
                colorResId = android.R.color.holo_green_light;
                break;
            case 1:
                colorResId = android.R.color.holo_orange_light;
                break;
            default:
                colorResId = android.R.color.holo_red_light;
        }
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);
        viewHolder.textViewNote.setBackgroundColor(color);

        // навешиваем setOnClickListener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNoteClickListener != null) {
                    onNoteClickListener.onNoteClick(note);
                }
            }
        });
    }

    // количество объектов в коллекции
    @Override
    public int getItemCount() {
        return notes.size();
    }


    // класс, который держит ссылку на View-элементы, с которыми можно работать
    class NotesViewHolder extends RecyclerView.ViewHolder {
        // ссылка на TextView, с которым работаем в onBindView
        private TextView textViewNote;

        // конструктор, принимает View, который создаем из макета
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }

    // слушатель клика по элементу списка
    interface OnNoteClickListener {
        void onNoteClick (Note note);
    }
}
