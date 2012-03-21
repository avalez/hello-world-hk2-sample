package sahoo.hello.startup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.ConfigParser;
import org.jvnet.hk2.config.TransactionFailure;

import com.sun.enterprise.module.bootstrap.Which;

@Service
public class DomainProxy implements Domain {
    @Inject
    Habitat habitat;
    
    URL parent;
    
    private Map<String, Domain> map = new HashMap<String, Domain>();
    
    public DomainProxy() {
        parent = DomainProxy.class.getResource("../../../");
        if (parent == null) { // inside jar
            try {
                parent = Which.jarFile(DomainProxy.class).toURI().toURL();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Domain me() {
        String name = ScopedDomain.name.get();
        Domain domain = map.get(name);
        if (domain == null) {
            synchronized(map) {
                if (!map.containsKey(name)){
                    populate(habitat, name);
                    domain = habitat.getComponent(Domain.class, name);
                    map.put(name, domain);
                }
            }
            if (domain == null) {
                domain = map.get(name);
            }
        }
        return domain;
    }
    
    @Override
    public ConfigBeanProxy getParent() {
        return me().getParent();
    }

    @Override
    public <T extends ConfigBeanProxy> T getParent(Class<T> type) {
        return me().getParent(type);
    }

    @Override
    public <T extends ConfigBeanProxy> T createChild(Class<T> type)
            throws TransactionFailure {
        return me().createChild(type);
    }

    @Override
    public ConfigBeanProxy deepCopy(ConfigBeanProxy parent)
            throws TransactionFailure {
        return me().deepCopy(parent);
    }

    @Override
    public void injectedInto(Object target) {
        me().injectedInto(target);
        
    }

    @Override
    public String getName() {
        return me().getName();
    }

    @Override
    public void setName(String name) {
        me().setName(name);
    }

    @Override
    public Test getTest() {
        return me().getTest();
    }

    @Override
    public void setTest(Test test) {
        me().setTest(test);
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

    private static final Logger LOGGER = Logger.getLogger(DomainProxy.class.getName());
}
