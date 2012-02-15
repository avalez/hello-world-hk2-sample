package sahoo.hello.startup;

import org.jvnet.hk2.component.Injectable;
import org.jvnet.hk2.config.Attribute;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.Configured;

@Configured
public interface Domain extends ConfigBeanProxy, Injectable, Named  {

    public static final String DOMAIN_NAME_PROPERTY = "administrative.domain.name";

    @Attribute
    String getName();
    void setName(String name);
}