package dmi.android.transparentaccounts;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmi.android.transparentaccounts.object.AccountTeaser;

/**
 * Created by DZCDMAY on 16.9.2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private final String TAG = "Transparent." + this.getClass().getName();

    private List<AccountTeaser> mValues;
    private IListAdapterListener adapterListener;

    public AccountListAdapter(List<AccountTeaser> items, IListAdapterListener l) {
        mValues = items;
        adapterListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.accountNumber.setText(mValues.get(position).getAccountNumber());
        holder.bankCode.setText(mValues.get(position).getBankCode());
        holder.transparencyFrom.setText(mValues.get(position).getTransparencyFrom());
        holder.transparencyTo.setText(mValues.get(position).getTransparencyTo());
        holder.publication.setText(mValues.get(position).getPublicationTo());
        holder.balance.setText(mValues.get(position).getBalance().toString());
        holder.currency.setText(mValues.get(position).getCurrency());
        holder.iban.setText(mValues.get(position).getIban());
        holder.actualizationDate.setText(mValues.get(position).getActualizationDate());

        /* Set tag to View for copying */
        holder.popupMenu.setTag(position);

        /* Handle click on list item */
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.onListItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Holder of Views
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView accountNumber;
        public final TextView bankCode;
        public final TextView transparencyFrom;
        public final TextView transparencyTo;
        public final TextView publication;
        public final TextView actualizationDate;
        public final TextView balance;
        public final TextView currency;
        public final TextView iban;
        public final ImageButton popupMenu;
        public AccountTeaser mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            accountNumber = (TextView) view.findViewById(R.id.acc_number);
            bankCode = (TextView) view.findViewById(R.id.bank);
            transparencyFrom = (TextView) view.findViewById(R.id.transparency_from);
            transparencyTo = (TextView) view.findViewById(R.id.transparency_to);
            publication = (TextView) view.findViewById(R.id.publication_to);
            actualizationDate = (TextView) view.findViewById(R.id.actualized);
            balance = (TextView) view.findViewById(R.id.amount);
            currency = (TextView) view.findViewById(R.id.currency);
            iban = (TextView) view.findViewById(R.id.iban);
            popupMenu = (ImageButton) view.findViewById(R.id.popup_menu);
        }
    }
}
