package com.larryhsiao.zendesk_sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entry point of sample
 */
public class EntryActivity extends AppCompatActivity {
    private final Config config = new LsConfig();
    private final List<Ticket> data = new ArrayList<>();
    private ArrayAdapter<Ticket> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);
        adapter = new ArrayAdapter<Ticket>(this, android.R.layout.simple_list_item_1, data) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final View item = super.getView(position, convertView, parent);
                ((TextView) item).setText(data.get(position).title());
                return item;
            }
        };
        final ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Ticket ticket = data.get(position);
                new AlertDialog.Builder(view.getContext())
                    .setTitle(ticket.title())
                    .setMessage(ticket.content())
                    .show();
            }
        });
        listView.setAdapter(adapter);
        findViewById(R.id.plusButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTicket();
            }
        });

        loadTickets();
    }

    private void loadTickets() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.addAll(new Tickets(config).value());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void newTicket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String id = UUID.randomUUID().toString().substring(0, 7);
                new NewTicket(config, "NewTitle " + id, "new content " + id).fire();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadTickets();
                    }
                });
            }
        }).start();
    }
}
