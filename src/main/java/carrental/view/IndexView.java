package carrental.view;

import carrental.MyUI;
import carrental.domain.User;
import carrental.presenter.UserPresenter;
import carrental.service.impl.MessageSourc;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@SpringView(name = "index", ui = MyUI.class)
@UIScope
public class IndexView extends CustomComponent implements View, ViewProvider {

    @Autowired
    private UserPresenter userPresenter;


    private TextField emailTf;
    private TextField passwordTf;
    private TextField registerEmailTf;
    private TextField registerPasswordTf;
    private TextField registerPasswordAgainTf;
    private TextField registerNameTf;
    private TextField registerAddressTf;
    private TextField registerAccountNumTf;

    private Button loginButton;
    private Button registerButton;

    private GridLayout loginGrid;
    private GridLayout registerGrid;

    private User user;

    private VerticalLayout page;

    private TabSheet tabSheet;

    private DateField date ;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UI.getCurrent().getPage().setTitle(MessageSourc.getMessage("index.loginToCarRental"));
        initTextField();
        initButton();
        textFieldSetValue();

        loginGrid = new GridLayout(3,4);
        registerGrid = new GridLayout(3,10);
        GridLayout originalGrid = new GridLayout(2, 3);

        loginGridAddComponent();
        registerGridAddComponent();
        registerGridAddLabel();

        tabSheet.addTab(loginGrid, MessageSourc.getMessage("index.Login"));
        tabSheet.addTab(registerGrid, MessageSourc.getMessage("index.Register"));

        addComponent(originalGrid);

        page.setComponentAlignment(originalGrid, Alignment.MIDDLE_CENTER);
        UI.getCurrent().setContent(page);
    }

    private void addComponent(GridLayout originalGrid) {
        originalGrid.addComponent(tabSheet,1,2);
        Label welcomeLabel = new Label(MessageSourc.getMessage("index.carRental"));
        welcomeLabel.addStyleName("font-label");
        originalGrid.addComponent(welcomeLabel,1,1);
        page.addComponent(originalGrid);

        registerGrid.setSizeFull();
        loginGrid.setSizeFull();
        tabSheet.setSizeFull();
        page.setSizeFull();
    }

    private void registerGridAddLabel() {
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.name")),1,1);
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.Email")),1,2);
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.address")),1,3);
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.password")),1,4);
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.passwordAgain")),1,5);
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.licenseExpDate")),1,6);
        registerGrid.addComponent(new Label(MessageSourc.getMessage("register.accountNumber")),1,7);
        registerGrid.addComponent(registerButton,1,9);
    }

    private void registerGridAddComponent() {
        registerGrid.addComponent(registerNameTf,2,1);
        registerGrid.addComponent(registerEmailTf,2,2);
        registerGrid.addComponent(registerAddressTf,2,3);
        registerGrid.addComponent(registerPasswordTf,2,4);
        registerGrid.addComponent(registerPasswordAgainTf,2,5);
        registerGrid.addComponent(date,2,6);
        registerGrid.addComponent(registerAccountNumTf,2,7);
    }

    private void loginGridAddComponent() {
        loginGrid.addComponent(emailTf, 2, 1);
        loginGrid.addComponent(passwordTf,2,2);
        loginButton.setIcon(VaadinIcons.USER);
        loginButton.addStyleName("button");
        loginGrid.addComponent(loginButton,1,3);
        loginGrid.addComponent(new Label(MessageSourc.getMessage("login.email")),1,1);
        loginGrid.addComponent(new Label(MessageSourc.getMessage("login.password")),1,2);
    }

    private void textFieldSetValue() {
        date.setValue(LocalDate.now());
        date.setPlaceholder(MessageSourc.getMessage("date"));
    }

    private void initButton() {
        date = new DateField();
        page = new VerticalLayout();
        tabSheet = new TabSheet();

        loginButton = new Button(MessageSourc.getMessage("index.Login"));
        registerButton = new Button(MessageSourc.getMessage("index.Register"));
        registerButton.addStyleName("button");
        registerButton.setIcon(VaadinIcons.PENCIL);
        loginButton.addClickListener(this::login);
        registerButton.addClickListener(this::signUp);
    }

    private void signUp(Button.ClickEvent clickEvent) {
        try{
            if(!registerPasswordTf.getValue().equals(registerPasswordAgainTf.getValue())){
                getNotification(MessageSourc.getMessage("register.NotEqualsPassword"),Notification.Type.ERROR_MESSAGE);
            }else{
                userPresenter.signup(registerNameTf.getValue(),registerAddressTf.getValue(),registerEmailTf.getValue(),registerPasswordTf.getValue(),date.getValue(),registerAccountNumTf.getValue());
                user = userPresenter.login(registerEmailTf.getValue(),registerPasswordTf.getValue());
                userPresenter.setUserRole(user);
                UI.getCurrent().getNavigator().navigateTo("index");
            }
        }catch (Exception e){
            getNotification(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }
    }

    private void login(Button.ClickEvent clickEvent) {
        try{
            user = userPresenter.login(emailTf.getValue(),passwordTf.getValue());
            if(user != null){
                userPresenter.setUserRole(user);
                userPresenter.setActualUser(user);
                UI.getCurrent().getNavigator().navigateTo("home");
            }
        }catch (Exception e){
            getNotification(e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }
    }

    private void getNotification(String message, Notification.Type type){
        Notification notif=new Notification(message, type);
        notif.setDelayMsec(500);
        notif.setPosition(Position.MIDDLE_CENTER);
        notif.show(Page.getCurrent());
    }
    private void initTextField() {
        emailTf = new TextField();
        registerEmailTf = new TextField();
        registerNameTf = new TextField();
        registerAddressTf = new TextField();
        registerPasswordTf = new PasswordField();
        registerPasswordAgainTf = new PasswordField();
        registerAccountNumTf = new TextField();
        passwordTf = new PasswordField();
    }

    @Override
    public String getViewName(String viewAndParameters) {
        return "index";
    }

    @Override
    public View getView(String viewName) {
        return this;
    }
}