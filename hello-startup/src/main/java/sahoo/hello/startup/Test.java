package sahoo.hello.startup;

import org.jvnet.hk2.component.Injectable;
import org.jvnet.hk2.config.Attribute;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.Configured;

@Configured
public interface Test extends ConfigBeanProxy, Injectable, Named {
    
    @Attribute
    String getName();
    void setName(String name);

}
