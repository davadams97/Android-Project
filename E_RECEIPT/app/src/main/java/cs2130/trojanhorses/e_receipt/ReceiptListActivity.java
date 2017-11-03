package cs2130.trojanhorses.e_receipt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aleix on 10/20/2017.
 */

public class ReceiptListActivity extends AppCompatActivity implements Callbackable {

    private ReceiptLab mReceiptLab;
    private RecyclerView mRecyclerView;
    private ReceiptAdapter mReceiptAdapter;

    private boolean mLoading;
    private boolean mDataLoaded;

    public void update() {updateRecyclerView();}


    //private final String URL = "http://137.149.157.18/CS2130/e-receipt/?date=20171001";

    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.d("TAG", "Before the Storm");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        Intent intent = getIntent();

        Log.d("TAG", "Before the Storm");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();


        Log.d("TAG", "After updateUI");


    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent (packageContext,ReceiptListActivity.class);
        return intent;
    }

    private void updateRecyclerView() {
        Log.d("TAG","update recycler view executed");

        List<Receipt> receipts = mReceiptLab.getReceipts();

        if (mReceiptAdapter == null) {
            mReceiptAdapter = new ReceiptAdapter(receipts);
            mRecyclerView.setAdapter(mReceiptAdapter);
        } else {
            mReceiptAdapter.notifyDataSetChanged();
        }
        Log.d("TAG","update recycler view finished executing");
    }


    /*private void updateUI(){
        URL url = buildURL();
        new eReceiptQuery().execute(url);
    }*/

    private void loadData() {
        mReceiptLab = ReceiptLab.get(ReceiptListActivity.this, this); //start asynctask
        updateRecyclerView();
    }

    private class ReceiptHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mPrice;
        private TextView mDate;

        private Receipt mReceipt;

        public ReceiptHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_receipt, parent, false));
            itemView.setOnClickListener(this);

            mDate = (TextView) itemView.findViewById(R.id.receipt_date);
            mPrice = (TextView) itemView.findViewById(R.id.price_total);
        }

        public void bind (Receipt receipt){
            mReceipt = receipt;
            mDate.setText(mReceipt.getDate());
            mPrice.setText("12.00");
        }

        @Override
        public void onClick(View view) {
            //Intent intent = ReceiptActivity.receiptsInstance(ReceiptListActivity.this, mReceipt);
            //startActivity(intent);
            int clicked = R.string.clicked_toast;
            Toast.makeText(ReceiptListActivity.this, clicked, Toast.LENGTH_SHORT).show();
        }
    }


    private class ReceiptAdapter extends RecyclerView.Adapter<ReceiptHolder>{

        private List<Receipt> mReceipts;

        public ReceiptAdapter(List<Receipt> receipts) {
            mReceipts = receipts;
        }

        @Override
        public ReceiptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ReceiptListActivity.this);
            return new ReceiptHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ReceiptHolder holder, int position) {
            Receipt receipt = mReceipts.get(position);
            holder.bind(receipt);
        }

        @Override
        public int getItemCount() {
            return mReceipts.size();
        }
    }
}