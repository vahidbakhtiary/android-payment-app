package ir.sep.android.merchantapp.ui.dashboard.menu;

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
        menus.add(new Menu(R.string.lbl_menu_request_roll, R.drawable.ic_paper_roll2, R.id.action_dashboardFragment_to_rollFragment));
        menus.add(new Menu(R.string.lbl_menu_supervisor_password, R.drawable.ic_key2, R.id.action_dashboardFragment_to_supervisorFragment));
        menus.add(new Menu(R.string.lbl_menu_faq, R.drawable.ic_paper_roll2, R.id.action_dashboardFragment_to_faqFragment));
       // menus.add(new Menu(R.string.lbl_menu_call_center, R.drawable.ic_paper_roll2, 0));
        //menus.add(new Menu(R.string.lbl_menu_change_address, R.drawable.ic_paper_roll2, 0));
        menus.add(new Menu(R.string.lbl_menu_massages, R.drawable.ic_paper_roll2, R.id.action_dashboardFragment_to_inboxFragment));
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