package ir.sep.android.merchantapp.ui.dashboard.menu;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.MenuItemBinding;


public class MenuAdapter extends ListAdapter<Menu, MenuViewHolder> {

    private static DiffUtil.ItemCallback<Menu> DIFF_UTIL = new DiffUtil.ItemCallback<Menu>() {
        @Override
        public boolean areItemsTheSame(@NonNull Menu oldItem, @NonNull Menu newItem) {
            return oldItem.getAction() == newItem.getAction();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Menu oldItem, @NonNull Menu newItem) {
            return true;
        }
    };

    private MenuListener listener;

    public MenuAdapter(MenuListener listener) {
        super(DIFF_UTIL);
        this.listener = listener;
        submitList(Menu.getMenus());
    }




    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.menu_item, parent, false),
                listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position));
    }

}

class MenuViewHolder extends RecyclerView.ViewHolder {

    private MenuItemBinding binding;
    private MenuListener listener;

    public MenuViewHolder(@NonNull MenuItemBinding binding, MenuListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(Menu menu) {
        binding.setListener(listener);
        binding.setMenu(menu);
        binding.executePendingBindings();
    }

}

