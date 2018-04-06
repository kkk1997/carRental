package carrental.service.impl;

import com.vaadin.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageSourc {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSourc.class);
    public static org.springframework.context.MessageSource messageSource;


    public static org.springframework.context.MessageSource getMessageSource() {
        return messageSource;
    }

    @Autowired
    public void setMessageSource(org.springframework.context.MessageSource messageSource) {
        MessageSourc.messageSource = messageSource;
    }

    public static String getMessage(String msg) {
        VaadinSession session = VaadinSession.getCurrent();
        Locale locale = null;
        if(session != null) {
            locale = session.getLocale();
        }
        if(locale == null) {
            locale = new Locale("hu", "HU");
        }
        try {
            return messageSource.getMessage(msg, null, locale);
        } catch (NoSuchMessageException e) {
            LOGGER.error("Missing key {} with locale {}", msg, locale, e);
            return msg;
        }
    }
}
