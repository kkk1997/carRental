package carrental.view;


import carrental.service.impl.MessageSourc;
import com.vaadin.ui.*;

public class ConfirmDialogView extends Window {

    private Button confirmBt ;

    public ConfirmDialogView(String msg,String width, String height) {
        super(MessageSourc.getMessage("confDialog.Confirm"));
        center();
        VerticalLayout page = new VerticalLayout();
        GridLayout components = new GridLayout(3,3);
        GridLayout originalGrid = new GridLayout(1,1);


        components.setWidth(width);
        components.setHeight(height);

        Label text = new Label(msg);
        confirmBt = new Button(MessageSourc.getMessage("confDialog.Yes"));
        Button cancelBt = new Button(MessageSourc.getMessage("confDialog.No"), event -> close());
        components.addComponent(text,1,1);
        components.addComponent(confirmBt,1,2);
        components.addComponent(cancelBt,2,2);


        originalGrid.addComponent(components);
        page.addComponent(originalGrid);
        setContent(page);

        setClosable(true);
    }

    public Button getConfirmBt() {
        return confirmBt;
    }
}
