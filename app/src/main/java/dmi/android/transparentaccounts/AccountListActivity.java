package dmi.android.transparentaccounts;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import dmi.android.transparentaccounts.network.APIError;
import dmi.android.transparentaccounts.network.APIManager;
import dmi.android.transparentaccounts.network.IAPIResponseListener;
import dmi.android.transparentaccounts.object.AccountTeaser;

/**
 * Activity that displays a list of transparent accounts with details
 * Provides detail view by open a new Activity
 */
public class AccountListActivity extends AppCompatActivity {

    private AccountListListener listener;
    private ArrayList<AccountTeaser> accounts;
    private RecyclerView listView;
    private ProgressBar progressBar;
    private AccountListAdapter adapter;
    private Context context;
    ClipboardManager myClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_list);
        context = this;
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /* Create listener for server response */
        listener = new AccountListListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Set progress visible - we're on loading */
        progressBar.setVisibility(View.VISIBLE);
        /* Get data from server */
        APIManager.getInstance().getTransparentAccountsList(listener);
    }

    /**
     * Listener of requests result
     */
    private class AccountListListener implements IAPIResponseListener, IListAdapterListener {

        @Override
        public void onListGotSuccessfully(ArrayList<?> data) {
            /* Dismiss progress */
            progressBar.setVisibility(View.GONE);

            accounts = (ArrayList<AccountTeaser>) data;
            if(adapter == null){
                /* Set adapter to ListView*/
                adapter = new AccountListAdapter(accounts, this);
                listView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onObjectGotSuccessfully(Object data) {

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
            /* Find clicked Account and start new Activity for details */
            AccountTeaser acc = accounts.get(position);
            Intent i = new Intent(context, AccountDetailsActivity.class);
            i.putExtra("number", acc.getAccountNumber());
            startActivity(i);
        }
    }

    /**
     *  Show Menu at each list item to provide copy of some Account details
     * @param view
     */
    public void showPopupMenu (final View view)
    {
        PopupMenu menu = new PopupMenu (this, view);
        final ClipData[] myClip = new ClipData[1];
        menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
        {
            @Override
            public boolean onMenuItemClick (MenuItem item)
            {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.copy_account:
                        myClip[0] = ClipData.newPlainText("text", accounts.get((int)view.getTag()).getAccountNumber());
                        myClipboard.setPrimaryClip(myClip[0]);
                        Toast.makeText(context, R.string.account_copied,Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.copy_iban:
                        myClip[0] = ClipData.newPlainText("text", accounts.get((int)view.getTag()).getIban());
                        myClipboard.setPrimaryClip(myClip[0]);
                        Toast.makeText(context, R.string.iban_copied,Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        menu.inflate (R.menu.list_item_menu);
        menu.show();
    }
}
