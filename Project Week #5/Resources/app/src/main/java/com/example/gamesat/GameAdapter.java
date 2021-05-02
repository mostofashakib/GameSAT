package com.example.gamesat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gamesat.GameContract.*;

public class GameAdapter extends RecyclerView.Adapter <GameAdapter.GameViewHolder>{

    private Context context;
    private Cursor cursor;
    private int wBoardSelect;

    public GameAdapter(Context context, Cursor cursor, int wBoardSelect){
        this.context = context;
        this.cursor = cursor;
        this.wBoardSelect = wBoardSelect;
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUserName;
        public TextView textViewTime;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.leader_board_item,parent,false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)){
            return; // no items to display
        }
        if (wBoardSelect > 0){ // positive word board select means the word board was chosen
            String username = cursor.getString(cursor.getColumnIndex(UsersWordCompletionTimeTable.COLUMN_USERNAME));
            long time = cursor.getLong(cursor.getColumnIndex(UsersWordCompletionTimeTable.COLUMN_TIME));
            holder.textViewUserName.setText(username);
            holder.textViewTime.setText(String.valueOf(time));
        } else {
            // if word is not selected then passage board is selected
            String username = cursor.getString(cursor.getColumnIndex(UsersPassageCompletionTimeTable.COLUMN_USERNAME));
            long time = cursor.getLong(cursor.getColumnIndex(UsersPassageCompletionTimeTable.COLUMN_TIME));
            holder.textViewUserName.setText(username);
            holder.textViewTime.setText(String.valueOf(time));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
