package Models;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<Workout> workouts = new ArrayList<>();
    private WorkoutItemClickListener workoutItemClickListener;

    public WorkoutAdapter(Activity activity, ArrayList<Workout> workouts) {
        this.activity = activity;
        this.workouts = workouts;
    }

    public WorkoutAdapter setWorkoutItemClickListener(WorkoutItemClickListener workoutItemClickListener) {
        this.workoutItemClickListener = workoutItemClickListener;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorkoutViewHolder workoutViewHolder = (WorkoutViewHolder) holder;
        Workout workout = getItem(position);

        workoutViewHolder.list_LBL_date.setText(workout.getDate());
        workoutViewHolder.list_LBL_type.setText(workout.getType());
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    private Workout getItem(int position) {
        return workouts.get(position);
    }

    public interface WorkoutItemClickListener {
        void workoutItemClick(Workout workout, int position);

    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView list_LBL_date;
        private MaterialTextView list_LBL_type;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            list_LBL_date = itemView.findViewById(R.id.list_LBL_date);
            list_LBL_type = itemView.findViewById(R.id.list_LBL_type);

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            workoutItemClickListener.workoutItemClick(getItem(getAdapterPosition()), getAdapterPosition());
                        }
                    }
            );
        }
    }
}
