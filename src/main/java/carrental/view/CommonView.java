package carrental.view;

import carrental.domain.Car;
import carrental.domain.Lend;
import carrental.presenter.CarPresenter;
import carrental.presenter.LendPresenter;
import carrental.presenter.UserPresenter;
import carrental.service.impl.MessageSourc;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ImageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class CommonView extends VerticalLayout implements View{

    protected AdminMenu adminMenu;
    protected UserMenu userMenu;

    @Autowired
    protected UserPresenter userPresenter;

    @Autowired
    protected CarPresenter carPresenter;

    @Autowired
    protected LendPresenter lendPresenter;

    protected final String widthPixelGrid = "1500px";
    protected final String heightPixelGrid = "600px";

    public CommonView() {
        adminMenu = new AdminMenu();
        userMenu = new UserMenu();
        addStyleName("home");
    }

    public Grid<Car> getCarComponents(List<Car> car) {
        Grid<Car> carGrid = new Grid(Car.class);
        Grid.Column<Car, ThemeResource> carThemeResourceColumn = carGrid.addColumn(p -> new ThemeResource(p.getImage()), new ImageRenderer<>());
        carGrid.setItems(car);

        carGrid.getColumn("type").setCaption(MessageSourc.getMessage("car.type"));
        carGrid.getColumn("licensePlateNumber").setCaption(MessageSourc.getMessage("car.licensePlateNumber"));
        carGrid.getColumn("intPrice").setCaption(MessageSourc.getMessage("car.carPrice"));
        carGrid.getColumn("age").setCaption(MessageSourc.getMessage("car.age"));
        carThemeResourceColumn.setCaption(MessageSourc.getMessage("car.picture"));
        carGrid.removeColumn("lend");
        carGrid.removeColumn("ID");
        carGrid.removeColumn("price");
        carGrid.removeColumn("image");

        return carGrid;
    }

    protected void lendGridAddColumn(Grid<Lend> lendGrid) {
        lendGrid.addColumn(Lend::getCar).setCaption((MessageSourc.getMessage("common.Car")));
        lendGrid.addColumn(Lend::getUser).setCaption((MessageSourc.getMessage("common.User")));
        lendGrid.addColumn(Lend::getStartDate).setCaption((MessageSourc.getMessage("common.StartDate")));
        lendGrid.addColumn(Lend::getEndDate).setCaption((MessageSourc.getMessage("common.EndDate")));
    }

    protected void getNotification(String message, Notification.Type type){
        Notification notif=new Notification(message, type);
        notif.setDelayMsec(500);
        notif.setPosition(Position.MIDDLE_CENTER);
        notif.show(Page.getCurrent());
    }
}
