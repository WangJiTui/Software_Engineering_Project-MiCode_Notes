import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<String> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteList = new ArrayList<>();
        // 初始化笔记数据
        for (int i = 0; i < 10; i++) {
            noteList.add("笔记 " + (i + 1));
        }

        noteAdapter = new NoteAdapter(noteList);
        recyclerView.setAdapter(noteAdapter);

        // 启用拖放功能
       

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                // 交换数据源中的数据
                Collections.swap(noteList, fromPosition, toPosition);
                // 通知适配器更新位置
                noteAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // 不实现滑动删除
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

        private List<String> notes;

        public NoteAdapter(List<String> notes) {
            this.notes = notes;
        }

        @NonNull
        @Override
        
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            holder.textView.setText(notes.get(position));
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        

            class NoteViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

           

           public NoteViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
