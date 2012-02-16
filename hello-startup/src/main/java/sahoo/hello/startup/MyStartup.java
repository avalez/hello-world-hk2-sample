package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.ConfigCode;
import org.jvnet.hk2.config.ConfigSupport;
import org.jvnet.hk2.config.SingleConfigCode;
import org.jvnet.hk2.config.TransactionFailure;

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
        /* NOT WORKING
        try {
            ConfigSupport.apply(new SingleConfigCode<Domain>() {
            //habitat.getComponent(ConfigSupport.class)._apply(new ConfigCode() {
                @Override
                public Object run(Domain domain) {
                //public Object run(ConfigBeanProxy... configBeanProxies) {
                    //Domain domain = (Domain) configBeanProxies[0];
                    domain.setName("test1");
                    return domain;
                }
                
            }, domain);
        } catch (TransactionFailure e) {
            e.printStackTrace();
            
        }
        */
        System.out.println("Hello " + domain.getName());
        System.out.println("Hello " + other.getName());
        
    }

    public void stop() {}

}
