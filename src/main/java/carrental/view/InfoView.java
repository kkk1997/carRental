package carrental.view;


import carrental.service.impl.MessageSourc;
import com.vaadin.ui.*;

/**
 * Created by Gani Leila on 2017.08.15..
 */
public class InfoView extends Window {

    private Button confirmBt ;

    public InfoView(String msg) {
        super("Info");
        center();

        Label text = new Label(msg);
        confirmBt = new Button(MessageSourc.getMessage("info.Ok"), event -> close());

        VerticalLayout page = new VerticalLayout(text,confirmBt);
        setContent(page);

        setClosable(true);
    }

    public Button getConfirmBt() {
        return confirmBt;
    }
}
