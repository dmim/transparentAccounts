package dmi.android.transparentaccounts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dmi.android.transparentaccounts.network.APIError;
import dmi.android.transparentaccounts.network.APIManager;
import dmi.android.transparentaccounts.network.IAPIResponseListener;
import dmi.android.transparentaccounts.object.AccountTeaser;
import dmi.android.transparentaccounts.object.TransactionTeaser;

public class AccountDetailsActivity extends AppCompatActivity {

    private  AccountTeaser accountTeaser;
    private  String number;
    private Context context;
    private AccountListener listener;
    private ArrayList<TransactionTeaser> transactions;
    private RecyclerView listView;
    private AccountTransactionsAdapter adapter;

    private  TextView accountNumber;
    private  TextView bankCode;
    private  TextView transparencyFrom;
    private  TextView transparencyTo;
    private  TextView publication;
    private  TextView actualizationDate;
    private  TextView balance;
    private  TextView currency;
    private  TextView iban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        context = this;

        Intent i = getIntent();
        number = i.getStringExtra("number");

        /* Show Back button */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        accountNumber = (TextView) findViewById(R.id.acc_number);
        bankCode = (TextView) findViewById(R.id.bank);
        transparencyFrom = (TextView) findViewById(R.id.transparency_from);
        transparencyTo = (TextView) findViewById(R.id.transparency_to);
        publication = (TextView) findViewById(R.id.publication_to);
        actualizationDate = (TextView) findViewById(R.id.actualized);
        balance = (TextView) findViewById(R.id.amount);
        currency = (TextView) findViewById(R.id.currency);
        iban = (TextView) findViewById(R.id.iban);

        listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));

        /* Create listener for server response */
        listener = new AccountListener();
        /* Get data from server */
        APIManager.getInstance().getTransparentAccountDetails(listener, number);
        APIManager.getInstance().getTransparentAccountTransactions(listener, number);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Update Account details
     */
    private void updateUI(){
        if(accountTeaser!=null) {
            accountNumber.setText(accountTeaser.getAccountNumber());
            bankCode.setText(accountTeaser.getBankCode());
            transparencyFrom.setText(accountTeaser.getTransparencyFrom());
            transparencyTo.setText(accountTeaser.getTransparencyTo());
            publication.setText(accountTeaser.getPublicationTo());
            balance.setText(accountTeaser.getBalance().toString());
            currency.setText(accountTeaser.getCurrency());
            iban.setText(accountTeaser.getIban());
            actualizationDate.setText(accountTeaser.getActualizationDate());
        }
    }

    /**
     * Listener of requests result
     */
    private class AccountListener implements IAPIResponseListener, IListAdapterListener {

        @Override
        public void onListGotSuccessfully(ArrayList<?> data) {
            transactions = (ArrayList<TransactionTeaser>) data;
            if(adapter == null){
                /* Set adapter to ListView*/
                adapter = new AccountTransactionsAdapter(transactions, this);
                listView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onObjectGotSuccessfully(Object data) {
            if(data instanceof AccountTeaser)
                accountTeaser = (AccountTeaser) data;
            /* Update some UI items according to given data */
            updateUI();
        }

        @Override
        public void onErrorGot(final APIError error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, error.getCode()+":"+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onListItemClick(int position) {

        }
    }
}
