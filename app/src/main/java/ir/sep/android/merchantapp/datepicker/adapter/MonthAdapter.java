package ir.sep.android.merchantapp.datepicker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.datepicker.data.MyMonth;

public class MonthAdapter extends ListAdapter<MyMonth, MonthViewHolder> {

    private static final DiffUtil.ItemCallback<MyMonth> DIFF_CALLBACK = new DiffUtil.ItemCallback<MyMonth>() {
        @Override
        public boolean areItemsTheSame(@NonNull MyMonth oldItem, @NonNull MyMonth newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull MyMonth oldItem, @NonNull MyMonth newItem) {
            return oldItem.isActive() == newItem.isActive();
        }
    };

    MonthListener listener;

    public MonthAdapter(MonthListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonthViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.month_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position), listener);
    }
}
