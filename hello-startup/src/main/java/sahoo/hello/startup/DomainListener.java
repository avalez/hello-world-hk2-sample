package sahoo.hello.startup;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.logging.Logger;

import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.config.ConfigBean;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.ConfigListener;
import org.jvnet.hk2.config.Dom;
import org.jvnet.hk2.config.TransactionListener;
import org.jvnet.hk2.config.UnprocessedChangeEvents;

@Service
// ConfigListener is supposed to be automatically registered for injected configuration object,
// but it does not happen, so it's registered manually in DomainXml, and it may be better.
// TransactionListener is an alternative also registered in DomainXml.
// CAUTION: both ways block ConfigSupport.apply thread
public class DomainListener implements ConfigListener, TransactionListener {

    @Override
    public UnprocessedChangeEvents changed(PropertyChangeEvent[] changes) {
        // assume there is a change and all changes are for one document
        // the latter is not necessarily true, transaction may be cross document
        PropertyChangeEvent event = changes[0];
        ConfigBeanProxy source = (ConfigBeanProxy) event.getSource();
        ConfigBean configBean = (ConfigBean) Dom.unwrap(source);
        LOGGER.info("Changed : " + configBean.document);
        
        return null;
    }

    @Override
    public void transactionCommited(List<PropertyChangeEvent> changes) {
        // assume there is a change and all changes are for one document
        // the latter is not necessarily true, transaction may be cross document
        PropertyChangeEvent event = changes.get(0);
        ConfigBeanProxy source = (ConfigBeanProxy) event.getSource();
        ConfigBean configBean = (ConfigBean) Dom.unwrap(source);
        LOGGER.info("Transcation Committed for document: " + configBean.document);
    }

    @Override
    public void unprocessedTransactedEvents(List<UnprocessedChangeEvents> changes) {

    }
    
    private static final Logger LOGGER = Logger.getLogger(DomainListener.class.getName());
}