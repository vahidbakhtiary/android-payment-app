package ir.sep.android.merchantapp.ui.roll;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.databinding.RequestHistoryItemBinding;


//ReceiptPaper Adapter
public class RequestHistoryAdapter extends ListAdapter<MerchantServiceEntity.ReceiptPaperList ,RequestHistoryViewHolder> {

    private static DiffUtil.ItemCallback<MerchantServiceEntity.ReceiptPaperList> DIFF_UTIL =new DiffUtil.ItemCallback<MerchantServiceEntity.ReceiptPaperList>() {
        @Override
        public boolean areItemsTheSame(@NonNull MerchantServiceEntity.ReceiptPaperList oldItem, @NonNull MerchantServiceEntity.ReceiptPaperList newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MerchantServiceEntity.ReceiptPaperList oldItem, @NonNull MerchantServiceEntity.ReceiptPaperList newItem) {
            return true;
        }
    };

    public RequestHistoryAdapter() {
        super(DIFF_UTIL);
    }

    @NonNull
    @Override
    public RequestHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestHistoryViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.request_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHistoryViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position));
    }
}

class RequestHistoryViewHolder extends RecyclerView.ViewHolder {

    RequestHistoryItemBinding binding;

    public RequestHistoryViewHolder(@NonNull RequestHistoryItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(MerchantServiceEntity.ReceiptPaperList data){


//        if (data.getReceiptPaperList())

        binding.setData(data);
        binding.executePendingBindings();
    }
}
