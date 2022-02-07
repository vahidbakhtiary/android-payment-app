package ir.sep.android.merchantapp.ui.setting.menu;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import ir.sep.android.merchantapp.R;

public class Menu {

    @StringRes
    private int title;
    @DrawableRes
    private int icon;
    private int action;

    private int notification;


    public Menu(int title, int icon, int action) {
        this.title = title;
        this.icon = icon;
        this.action = action;
        this.notification = notification;
    }
    public Menu(int title, int icon, int action, int notification) {
        this.title = title;
        this.icon = icon;
        this.action = action;
        this.notification = notification;
    }

    public static List<Menu> getMenus() {
        List<Menu> menus = new ArrayList<>();

        menus.add(new Menu(R.string.lbl_menu_update_app, R.drawable.ic_baseline_refresh_24, 0));
        menus.add(new Menu(R.string.lbl_menu_aboutus, R.drawable.ic_baseline_info_24, R.id.action_settingFragment_to_aboutFragment));
        menus.add(new Menu(R.string.lbl_menu_exit, R.drawable.ic_baseline_exit_to_app_24, R.id.action_settingFragment_to_aboutFragment));
        return menus;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }
}