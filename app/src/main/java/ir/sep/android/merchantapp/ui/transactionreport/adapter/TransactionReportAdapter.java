package ir.sep.android.merchantapp.ui.transactionreport.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Rahsepar;
import ir.sep.android.merchantapp.databinding.ReportItemBinding;

public class TransactionReportAdapter extends ListAdapter<Rahsepar, TransactionReportViewHolder> {

    private static DiffUtil.ItemCallback<Rahsepar> DIFF_UTIL = new DiffUtil.ItemCallback<Rahsepar>() {
        @Override
        public boolean areItemsTheSame(@NonNull Rahsepar oldItem, @NonNull Rahsepar newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rahsepar oldItem, @NonNull Rahsepar newItem) {
            return oldItem.isExpanded() == newItem.isExpanded();
        }
    };

    private TransactionReportListener listener;

    public TransactionReportAdapter(TransactionReportListener listener) {
        super(DIFF_UTIL);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionReportViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.report_item, parent, false),
                listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionReportViewHolder holder, int position) {
        getCurrentList().get(position).setPositionInList(position);
        holder.bind(getCurrentList().get(position));
    }

}

class TransactionReportViewHolder extends RecyclerView.ViewHolder {

    ReportItemBinding binding;
    TransactionReportListener listener;

    public TransactionReportViewHolder(@NonNull ReportItemBinding binding, TransactionReportListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    void bind(Rahsepar rahsepar) {
        binding.setListener(listener);
        binding.setRahsepar(rahsepar);
        binding.executePendingBindings();
    }
}
