package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.component.Injectable;
import org.jvnet.hk2.config.Attribute;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.Configured;
import org.jvnet.hk2.config.Element;

@Configured
@Scoped(DomainScope.class)
public interface Domain extends ConfigBeanProxy, Injectable, Named  {
    @Attribute
    String getName();
    void setName(String name);
    
    @Element
    Test getTest();
    void setTest(Test test);
}