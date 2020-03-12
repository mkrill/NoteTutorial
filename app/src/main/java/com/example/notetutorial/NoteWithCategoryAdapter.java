package com.example.notetutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteWithCategoryAdapter extends ListAdapter<NoteWithCategory, NoteWithCategoryAdapter.NoteWithCategoryHolder> {

    private OnItemClickListener listener;

    public NoteWithCategoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<NoteWithCategory> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteWithCategory>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoteWithCategory oldItem, @NonNull NoteWithCategory newItem) {
            return oldItem.node.getId() == newItem.node.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteWithCategory oldItem, @NonNull NoteWithCategory newItem) {
            return oldItem.node.getTitle().equals(newItem.node.getTitle())
                    && oldItem.node.getDescription().equals(newItem.node.getDescription())
                    && (oldItem.node.getPriority() == newItem.node.getPriority())
                    && (oldItem.node.getCategoryId() == newItem.node.getCategoryId());
        }
    };

    @NonNull
    @Override
    public NoteWithCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteWithCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteWithCategoryHolder holder, int position) {
        NoteWithCategory currentNoteWithCategory = getItem(position);
        holder.textViewTitle.setText(currentNoteWithCategory.node.getTitle());
        holder.textViewDescription.setText(currentNoteWithCategory.node.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNoteWithCategory.node.getPriority()));
        holder.textViewCategory.setText(String.valueOf(currentNoteWithCategory.category.getName()));
    }

    public NoteWithCategory getNoteWithCategoryAt(int position) {
        return getItem(position);
    }

    class NoteWithCategoryHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewCategory;

        public NoteWithCategoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NoteWithCategory noteWithCategory);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
