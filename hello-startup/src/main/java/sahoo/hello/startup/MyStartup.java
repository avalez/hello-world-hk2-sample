package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;

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
	@Inject
	Domain domain;
	
    public void setStartupContext(StartupContext context) {
    }

    public void start() {
        System.out.println("Hello " + domain.getName());
        
    }

    public void stop() {}

}
