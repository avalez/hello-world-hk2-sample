package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.TransactionFailure;

public class DomainProxy implements Domain {
    @Inject(name="MyScopedDomain")
    private ScopedDomain scopedDomain;
    
    private Domain me() {
        return scopedDomain.getDomain();
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

}
