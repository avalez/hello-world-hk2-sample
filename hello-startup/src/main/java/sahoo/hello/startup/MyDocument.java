package sahoo.hello.startup;

import javax.xml.stream.XMLStreamReader;

import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigBean;
import org.jvnet.hk2.config.ConfigModel;
import org.jvnet.hk2.config.DomDocument;

// Override make to create ConfigBean instead of Dom for ConfigSupport.apply(ConfigSupport.java:135)
public class MyDocument extends DomDocument<ConfigBean> {
    public MyDocument(final Habitat habitat) {
        super(habitat);
    }
    
    @Override
    public ConfigBean make(final Habitat habitat, XMLStreamReader xmlStreamReader, ConfigBean dom, ConfigModel configModel) {
        // by default, people get the translated view.
        return new ConfigBean(habitat,this, dom, configModel, xmlStreamReader);
    }

}
