package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ScopedDomain {
    Domain getDomain(String name);
}
