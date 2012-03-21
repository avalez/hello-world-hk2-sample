package sahoo.hello.startup;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ScopedDomain {
    Domain getDomain();
    
    ThreadLocal <String> name = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "1";
        }
    };
}
