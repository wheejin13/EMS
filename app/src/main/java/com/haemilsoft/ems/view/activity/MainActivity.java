package com.haemilsoft.ems.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.haemilsoft.ems.R;
import com.haemilsoft.ems.view.notification.EMSNotificationManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeLayout = findViewById(R.id.main_swipe);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 4000); // Delay in millis

                Toast.makeText(getApplicationContext(), "데이터 갱신 중", Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light));
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, new LinearLayoutManager(this).getOrientation()));

        List<String> person = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            person.add(String.valueOf(i));
        }

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(MainActivity.this, getApplicationContext(), person);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onInit() {

    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<String> person;
        private final Context mContext;
        private final Activity mActivity;

        public RecyclerViewAdapter(Activity activity, Context context, List<String> person) {
            this.person = person;
            this.mContext = context;
            this.mActivity = activity;
        }

        @Override
        public int getItemCount() {
            return person.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name_user);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(mActivity, ChartActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        mContext.startActivity(intent);

                    }
                });
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        // 재활용 되는 View가 호출, Adapter가 해당 position에 해당하는 데이터를 결합
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            String p = person.get(position);

            // 데이터 결합
            holder.name.setText(p);
        }

        private void removeItemView(int position) {
            person.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, person.size()); // 지워진 만큼 다시 채워넣기.
        }
    }
}
