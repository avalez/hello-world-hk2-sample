package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigBean;
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
    
    @Inject
    DomainXml domainXml;
    
    public void setStartupContext(StartupContext context) {
    }

    public void start() {
        Domain other = habitat.getComponent(Domain.class, "test2");
        Test test = habitat.getComponent(Test.class, "test2");
        try {
            // domain can not be modified, see WriteableView.setter(WriteableView.java:235)
            ConfigSupport.apply(new SingleConfigCode<Test>() {
            //ConfigSupport.apply(new ConfigCode() {
                @Override
                public Object run(Test test) {
                //public Object run(ConfigBeanProxy... configBeanProxies) {
                    //Test test = (Test) configBeanProxies[0];
                    test.setName("test1");
                    //test = (Test) configBeanProxies[1];
                    //test.setName("test1");
                    return test;
                }
                
            }, test/*, habitat.getComponent(Test.class, "test")*/);
        } catch (TransactionFailure e) {
            e.printStackTrace();            
        }

        System.out.println("Domain " + domain.getName() + ", Test " + domain.getTest().getName());
        System.out.println("Domain " + other.getName() + ", Test " + other.getTest().getName());
        
        MyDocument newDocument = domainXml.create("new-test");
        ConfigBean root = (ConfigBean) newDocument.getRoot();
        Domain newDomain = root.getProxy(Domain.class);
        
        try {
            ConfigSupport.apply(new SingleConfigCode<Domain>() {
                @Override
                public Object run(Domain domain) throws TransactionFailure {
                    Test test = domain.createChild(Test.class);
                    test.setName("test3");
                    domain.setTest(test);
                    return domain;
                }
                
            }, newDomain);
        } catch (TransactionFailure e) {
            e.printStackTrace();            
        }
        
        System.out.println("New Domain " + newDomain.getName() + ", Test " + newDomain.getTest().getName());

    }

    
    public void stop() {}

}
