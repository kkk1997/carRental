package carrental.view;

import carrental.service.impl.MessageSourc;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

/**
 * Created by Gani Leila on 2017.08.22..
 */
public class UserMenu extends MenuBar {

    UserMenu() {
        this.addItem(MessageSourc.getMessage("user.ActualOffer"), a -> UI.getCurrent().getNavigator().navigateTo("home"));
        this.addItem(MessageSourc.getMessage("user.Cars"), a -> UI.getCurrent().getNavigator().navigateTo("carView"));
        this.addItem(MessageSourc.getMessage("user.Data"), a -> UI.getCurrent().getNavigator().navigateTo("userDataView"));
        this.addItem(MessageSourc.getMessage("user.Exit"), a ->  UI.getCurrent().getNavigator().navigateTo("index"));
    }
}
