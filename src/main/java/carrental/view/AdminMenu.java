package carrental.view;

import carrental.service.impl.MessageSourc;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

public class AdminMenu extends MenuBar{

    AdminMenu() {
        this.addItem(MessageSourc.getMessage("admin.ActualLend"), a -> UI.getCurrent().getNavigator().navigateTo("home"));
        this.addItem(MessageSourc.getMessage("admin.Users"), a -> UI.getCurrent().getNavigator().navigateTo("userView"));
        this.addItem(MessageSourc.getMessage("admin.Cars"), a -> UI.getCurrent().getNavigator().navigateTo("carView"));
        this.addItem(MessageSourc.getMessage("admin.Lending"), a -> UI.getCurrent().getNavigator().navigateTo("lendView"));
        this.addItem(MessageSourc.getMessage("admin.Exit"), a -> UI.getCurrent().getNavigator().navigateTo("index"));
    }

}
