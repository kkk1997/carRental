package carrental;

import carrental.domain.User;
import carrental.service.impl.MessageSourc;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;

@Theme("mytheme")
@SpringUI
@UIScope
@PreserveOnRefresh
public class MyUI extends UI {

    @Autowired
    private SpringViewProvider viewProvider;

    private User user;

    private Navigator navigator;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();
        addStyleName("mytheme");
        getPage().setTitle(MessageSourc.getMessage("home.title"));
        navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.navigateTo("index");
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false, widgetset = "VAADIN.widgetsets.carrental.web.widgetset.MyAppWidgetset")
    public static class MyUIServlet extends VaadinServlet {
    }

}
