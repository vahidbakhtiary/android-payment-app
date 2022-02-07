package ir.sep.android.merchantapp.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentSettingBinding;
import ir.sep.android.merchantapp.ui.setting.menu.Menu;
import ir.sep.android.merchantapp.ui.setting.menu.MenuAdapter;
import ir.sep.android.merchantapp.ui.setting.menu.MenuListener;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class SettingFragment extends BottomSheetDialogFragment implements MenuListener {

    private NavController navController;
    private FragmentSettingBinding binding;
    private MenuAdapter menuAdapter;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(getParentFragment().getView());
        initMenu();
    }


    private void initMenu() {
        menuAdapter = new MenuAdapter(this);
        binding.rvMenu.setHasFixedSize(true);
        binding.rvMenu.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
        binding.rvMenu.setAdapter(menuAdapter);
    }



    @Override
    public void onMenuClicked(Menu menu) {
        //Toast.makeText(getContext(), menu.getTitle(), Toast.LENGTH_SHORT).show();
        //navController.navigate(menu.getAction());
        //dismiss();

        switch (menu.getTitle())
        {
            case R.string.lbl_menu_suggest:
                shareWithAnotherApp();
                break;
            case R.string.lbl_menu_aboutus:
                navController.navigate(menu.getAction());
                break;
            case R.string.lbl_menu_exit:
                sharedPreferencesHelper.insert(Const.SHARED_PREF_IS_AUTHORIZED_SESSION_KEY,"false");
                getActivity().finishAffinity();
                System.exit(0);

                break;
        }
    }

    private void updateApp()
    {
        Uri uriUrl = Uri.parse(Const.APP_BASEURL);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void shareWithAnotherApp()
    {
        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = getString(R.string.alert_suggest_message) + "\n\n\n" + Const.APP_BASEURL;
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, getString(R.string.alert_share_using)));
    }
}
