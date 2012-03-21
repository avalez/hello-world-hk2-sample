package sahoo.hello.startup;

import org.glassfish.hk2.ComponentException;
import org.jvnet.hk2.annotations.FactoryFor;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Factory;

@Service
@FactoryFor(Domain.class)
public class MyFactory implements Factory<Domain> {
    @Inject
    private DomainProxy domainProxy;
    
    @Override
    public Domain get() throws ComponentException {
        return domainProxy;
    }

}
