package sahoo.hello.startup;

import java.util.HashMap;
import java.util.Map;

import org.glassfish.hk2.ScopeInstance;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Scope;

import com.sun.hk2.component.ScopeInstanceImpl;

@Service
public class DomainScope extends Scope {
    
    private Map<String, ScopeInstance> map = new HashMap<String, ScopeInstance>();
    
    @Override
    @SuppressWarnings("rawtypes")
    public ScopeInstance current() {
        String name = ScopedDomain.name.get();
        if (!map.containsKey(name))  {
            synchronized(map) {
                if (!map.containsKey(name))  {
                    map.put(name, new ScopeInstanceImpl(name, new HashMap())); 
                }                
            }
        }
        return map.get(name);
    }

}
