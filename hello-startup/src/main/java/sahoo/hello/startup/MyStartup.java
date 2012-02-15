package sahoo.hello.startup;

import java.util.Iterator;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;

import com.sun.enterprise.module.bootstrap.ModuleStartup;
import com.sun.enterprise.module.bootstrap.StartupContext;

/**
 * This is the first class to be instantiated by HK2 module system.
 * This is the entry point to application domain.
 *
 * @author Sanjeeb.Sahoo@Sun.COM
 */
@Service
public class MyStartup implements ModuleStartup
{
    @Inject(name="test")
    Domain domain;

    @Inject
    protected Habitat habitat;
    
    public void setStartupContext(StartupContext context) {
    }

    public void start() {
        Domain other = habitat.getByType(Domain.class); //getComponent(Domain.class, "test");
        Iterator<String> contracts = habitat.getAllTypes();
        while(contracts.hasNext()) {
            System.out.println(contracts.next());
        }
        System.out.println("Hello " + domain.getName());
        System.out.println("Hello " + other.getName());
        
    }

    public void stop() {}

}
