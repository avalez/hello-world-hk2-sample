package sahoo.hello.startup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

    import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigParser;

import com.sun.enterprise.module.ModulesRegistry;
import com.sun.enterprise.module.bootstrap.StartupContext;
import com.sun.enterprise.module.bootstrap.Which;
import com.sun.enterprise.module.single.StaticModulesRegistry;

@Service
public class MyScopedDomain implements ScopedDomain {
    
    Map<String, Habitat> habitats = new HashMap<String, Habitat>();

    URL parent;
    
    public MyScopedDomain() {
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
    public Domain getDomain(String name) {
        Habitat habitat = getHabitat(name);
        return  habitat.getComponent(Domain.class);
    }
    
    protected Habitat getHabitat(String name) {
        if (!habitats.containsKey(name))  {
            synchronized(habitats) {
                if (!habitats.containsKey(name))  {
                    habitats.put(name, getNewHabitat(name)); 
                }                
            }
        }
        return habitats.get(name);
    }

    private Habitat getNewHabitat(String name) {
        Properties p = new Properties();
        ModulesRegistry registry = new StaticModulesRegistry(MyScopedDomain.class.getClassLoader(), new StartupContext(p));
        final Habitat habitat = registry.createHabitat("default");
        
        populate(habitat, name);
        return habitat;
    }
    
    private void populate(Habitat habitat, String name) {
        String filePath = parent.toString() + "/domain" + name + ".xml";
        ConfigParser parser = new ConfigParser(habitat);
        URL fileUrl = null;
        try {
            fileUrl = new URL(filePath);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        parser.parse(fileUrl, new MyDocument(habitat, fileUrl));
    }}
