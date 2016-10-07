package dmi.android.transparentaccounts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import dmi.android.transparentaccounts.object.AccountTeaser;
import dmi.android.transparentaccounts.object.TransactionTeaser;

/**
 * Created by DZCDMAY on 7.10.2016.
 */
public class AccountTransactionsAdapter  extends RecyclerView.Adapter<AccountTransactionsAdapter.ViewHolder> {

    private final String TAG = "Transparent." + this.getClass().getName();

    private List<TransactionTeaser> mValues;
    private IListAdapterListener adapterListener;

    public AccountTransactionsAdapter(List<TransactionTeaser> items, IListAdapterListener l) {
        mValues = items;
        adapterListener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.value.setText(""+mValues.get(position).getAmount().getValue());
        holder.currency.setText(mValues.get(position).getAmount().getCurrency());
        holder.dueDate.setText(mValues.get(position).getDueDate());
        holder.processingDate.setText(mValues.get(position).getProcessingDate());
        holder.fromAccNumber.setText(mValues.get(position).getSender().getAccountNumber());
        holder.fromIBAN.setText(mValues.get(position).getSender().getIban());
        holder.fromBank.setText(mValues.get(position).getSender().getBankCode());
        holder.fromSpecSymb.setText(mValues.get(position).getSender().getSpecificSymbol());
        holder.fromConstantSymb.setText(mValues.get(position).getSender().getConstantSymbol());

        /* no click handle is needed */
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
        public final TextView value;
        public final TextView currency;
        public final TextView dueDate;
        public final TextView processingDate;
        public final TextView fromAccNumber;
        public final TextView fromIBAN;
        public final TextView fromBank;
        public final TextView fromSpecSymb;
        public final TextView fromConstantSymb;
        public TransactionTeaser mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            value = (TextView) view.findViewById(R.id.value);
            dueDate = (TextView) view.findViewById(R.id.dueDate);
            processingDate = (TextView) view.findViewById(R.id.processingDate);
            fromAccNumber = (TextView) view.findViewById(R.id.fromAccNumber);
            fromIBAN = (TextView) view.findViewById(R.id.fromIBAN);
            fromBank = (TextView) view.findViewById(R.id.fromBank);
            fromSpecSymb = (TextView) view.findViewById(R.id.fromSpecSymb);
            currency = (TextView) view.findViewById(R.id.currency);
            fromConstantSymb = (TextView) view.findViewById(R.id.fromConstantSymb);
        }
    }
}
