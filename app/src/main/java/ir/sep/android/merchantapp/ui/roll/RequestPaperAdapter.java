package ir.sep.android.merchantapp.ui.roll;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.databinding.RequestHistoryItemBinding;
import ir.sep.android.merchantapp.databinding.RequestPaperItemBinding;


//RequestPaper Adapter
public class RequestPaperAdapter extends ListAdapter<MerchantServiceBaseEntity.RequestPaperList, RequestPaperListViewHolder> {

    private static DiffUtil.ItemCallback<MerchantServiceEntity.RequestPaperList> DIFF_UTIL = new DiffUtil.ItemCallback<MerchantServiceEntity.RequestPaperList>() {
        @Override
        public boolean areItemsTheSame(@NonNull MerchantServiceEntity.RequestPaperList oldItem, @NonNull MerchantServiceEntity.RequestPaperList newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MerchantServiceEntity.RequestPaperList oldItem, @NonNull MerchantServiceEntity.RequestPaperList newItem) {
            return true;
        }
    };

    public RequestPaperAdapter() {
        super(DIFF_UTIL);
    }

    @NonNull
    @Override
    public RequestPaperListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestPaperListViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.request_paper_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestPaperListViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position));
    }
}

class RequestPaperListViewHolder extends RecyclerView.ViewHolder {

    RequestPaperItemBinding binding;

    public RequestPaperListViewHolder(@NonNull RequestPaperItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(MerchantServiceBaseEntity.RequestPaperList data) {


//        if (data.getReceiptPaperList())

        binding.setData(data);
        binding.executePendingBindings();
    }
}
