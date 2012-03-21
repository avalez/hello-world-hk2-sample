package sahoo.hello.startup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.glassfish.hk2.ComponentException;
import org.jvnet.hk2.annotations.FactoryFor;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Factory;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigParser;

import com.sun.enterprise.module.bootstrap.Which;

@Service
@FactoryFor(Domain.class)
public class DomainFactory implements Factory<Domain> {

    @Inject
    Habitat habitat;

    URL parent;
    
    public DomainFactory() {
        parent = DomainXml.class.getResource("../../../");
        if (parent == null) { // inside jar
            try {
                parent = Which.jarFile(MyScopedDomain.class).toURI().toURL();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public Domain get() throws ComponentException {
        // it's not available yet, create
        String name = ScopedDomain.name.get();
        populate(habitat, name);
        Domain domain = habitat.getComponent(Domain.class, name);
        return domain;
    }

    private void populate(Habitat habitat, String name) {
        String filePath = parent.toString() + "/domain" + name + ".xml";
        LOGGER.info("Loading " + filePath);
        ConfigParser parser = new ConfigParser(habitat);
        URL fileUrl = null;
        try {
            fileUrl = new URL(filePath);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        parser.parse(fileUrl, new MyDocument(habitat, fileUrl));
    }

    private static final Logger LOGGER = Logger.getLogger(NewScopedDomain.class.getName());
}
