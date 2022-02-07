package ir.sep.android.merchantapp.datepicker.adapter;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.MonthItemBinding;
import ir.sep.android.merchantapp.datepicker.data.MyMonth;

public class MonthViewHolder extends RecyclerView.ViewHolder {

    MonthItemBinding binding;

    public MonthViewHolder(@NonNull MonthItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(MyMonth month, MonthListener listener) {

        Resources resources = binding.getRoot().getResources();

        binding.cvMonth.setOnClickListener(v -> listener.onActiveMonthChange(month));
        binding.tvMonth.setText(month.toString());

        if (month.isActive()) {
            binding.cvMonth.setCardBackgroundColor(resources.getColor(R.color.colorOnPrimary));
            binding.tvMonth.setTextColor(resources.getColor(R.color.colorPrimary));
        } else {
            binding.cvMonth.setCardBackgroundColor(resources.getColor(R.color.colorOnPrimaryTransparent));
            binding.tvMonth.setTextColor(resources.getColor(R.color.colorOnPrimary));
        }

    }

}
