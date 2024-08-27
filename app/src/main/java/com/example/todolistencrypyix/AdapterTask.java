package com.example.todolistencrypyix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterTask extends ListAdapter<TaskModel,AdapterTask.TaskHolder> {
    private OnItemClickListener listener;
    private  List<TaskModel>taskModels;
    private ViewModel viewModel;

    AdapterTask(ViewModel viewModel) {
        super(DIFF_CALLBACK);

        this.viewModel = viewModel;
    }

    public interface OnItemClickListener {
        void onItemClick(TaskModel model);
    }
    public void settasks(List<TaskModel>taskModels){
        this.taskModels=taskModels;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        TaskModel model = getTASKAt(position);
        holder.description.setText(model.getTaskDescription());
        holder.title.setText(model.getTitleTask());
        holder.checkBox.setChecked(model.isChecked());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    model.isChecked=isChecked;
                   viewModel.update(model);
                }
            });


    }

    private static final DiffUtil.ItemCallback<TaskModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TaskModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull TaskModel oldItem, @NonNull TaskModel newItem) {
                    // below line is to check the course name, description and course duration.
                    return oldItem.getTitleTask().equals(newItem.getTitleTask()) &&
                            oldItem.getTaskDescription().equals(newItem.getTaskDescription());
                }
            };

    public TaskModel getTASKAt(int position) {
        return getItem(position);
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        CheckBox checkBox;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleview);
            description = itemView.findViewById(R.id.descview);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }



    }
}


