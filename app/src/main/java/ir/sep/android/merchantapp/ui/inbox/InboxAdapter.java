package ir.sep.android.merchantapp.ui.inbox;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.data.entities.MerchantServiceEntity;
import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.databinding.InboxItemBinding;
import ir.sep.android.merchantapp.databinding.RequestHistoryItemBinding;


//ReceiptPaper Adapter
public class InboxAdapter extends ListAdapter<Inbox, InboxViewHolder> {


    private static DiffUtil.ItemCallback<Inbox> DIFF_UTIL = new DiffUtil.ItemCallback<Inbox>() {
        @Override
        public boolean areItemsTheSame(@NonNull Inbox oldItem, @NonNull Inbox newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Inbox oldItem, @NonNull Inbox newItem) {
            return true;
        }
    };

   public InboxAdapter()
    {
        super(DIFF_UTIL);
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InboxViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.inbox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position));
    }
}

class InboxViewHolder extends RecyclerView.ViewHolder {

    InboxItemBinding binding;

    public InboxViewHolder(@NonNull InboxItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Inbox data){


//        if (data.getReceiptPaperList())

        binding.setData(data);
        binding.executePendingBindings();
    }
}
