package sahoo.hello.startup;

import com.sun.enterprise.module.bootstrap.Populator;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.config.ConfigParser;
import org.jvnet.hk2.config.DomDocument;

import java.net.URL;
import java.util.logging.Logger;

/**
 * @author Kohsuke Kawaguchi
 */
@Service
public class DomainXml implements Populator {
    public void run(ConfigParser parser) {
        URL url = this.getClass().getResource("/domain.xml");
        LOGGER.info("Loading " + url);
        DomDocument document = parser.parse(url);
    }

    private static final Logger LOGGER = Logger.getLogger(DomainXml.class.getName());
}
