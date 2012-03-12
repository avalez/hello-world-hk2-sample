package sahoo.hello.startup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigParser;

import com.sun.enterprise.module.ModulesRegistry;
import com.sun.enterprise.module.bootstrap.StartupContext;
import com.sun.enterprise.module.bootstrap.Which;
import com.sun.enterprise.module.single.StaticModulesRegistry;

@Service(name="Hk2ScopedDomain")
@Scoped(DomainScope.class)
public class Hk2ScopedDomain implements ScopedDomain  {

    // scope habitat!
    final Habitat habitat;

    final URL parent;
    
    final String name;
    
    public Hk2ScopedDomain() {
        URL u = DomainXml.class.getResource("../../../");
        if (u == null) { // inside jar
            try {
                u = Which.jarFile(Hk2ScopedDomain.class).toURI().toURL();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        parent = u;

        Properties p = new Properties();
        ModulesRegistry registry = new StaticModulesRegistry(Hk2ScopedDomain.class.getClassLoader(), new StartupContext(p));
        habitat = registry.createHabitat("default");
        
        name = ScopedDomain.name.get();
        
        populate();
    }
    
    public Domain getDomain() {
        return  habitat.getComponent(Domain.class);
    }
    

    private void populate() {
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

    private static final Logger LOGGER = Logger.getLogger(Hk2ScopedDomain.class.getName());
}

