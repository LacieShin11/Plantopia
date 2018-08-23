package plantopia.sungshin.plantopia.customView;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

import plantopia.sungshin.plantopia.R;

public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
    private int position;

    public MyMenuItemClickListener(int positon) {
        this.position = positon;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_edit:
                break;

            case R.id.menu_delete:
                break;
        }
        return false;
    }
}
