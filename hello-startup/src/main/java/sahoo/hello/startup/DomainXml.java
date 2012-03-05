package sahoo.hello.startup;

import com.sun.enterprise.module.bootstrap.Populator;
import com.sun.enterprise.module.bootstrap.Which;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigBean;
import org.jvnet.hk2.config.ConfigParser;
import org.jvnet.hk2.config.DomDocument;
import org.jvnet.hk2.config.Transactions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

@Service
public class DomainXml implements Populator {
    @Inject
    Habitat habitat;
    
    @Inject
    DomainListener changesListener;
    
    public void run(ConfigParser parser) {
        try {
            URL parent = DomainXml.class.getResource("../../../");
            if (parent == null) { // inside jar
                parent = Which.jarFile(DomainXml.class).toURI().toURL();
            }
            LOGGER.info("Looking for domain*.xml files in " + parent);
            Collection<URL> resources = getResources(parent, Pattern.compile(".*/?domain.*xml"));
            
            if (resources.size() == 0) {
                LOGGER.severe("No config files to load");
            }
            for (URL res: resources) {
                LOGGER.info("Loading " + res);
                @SuppressWarnings("rawtypes")
                DomDocument doc = parser.parse(res, new MyDocument(habitat, res));
                // register changes listener
                ((ConfigBean)doc.getRoot()).addListener(changesListener);
               
            }
            // alternative change listener:
            habitat.getComponent(Transactions.class).addTransactionsListener(changesListener);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // http://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory
    private static Collection<URL> getResources(final URL element,
            final Pattern pattern) throws URISyntaxException {
        final ArrayList<URL> retval = new ArrayList<URL>();
        final File file = new File(element.toURI());
        if (file.isDirectory()) {
            retval.addAll(getResourcesFromDirectory(file, pattern));
        } else {
            retval.addAll(getResourcesFromJarFile(file, pattern));
        }
        return retval;
    }

    private static Collection<URL> getResourcesFromJarFile(final File file,
            final Pattern pattern) {
        final ArrayList<URL> retval = new ArrayList<URL>();
        ZipFile zf;
        try {
            zf = new ZipFile(file);
        } catch (final ZipException e) {
            throw new Error(e);
        } catch (final IOException e) {
            throw new Error(e);
        }
        @SuppressWarnings("rawtypes")
        final Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = pattern.matcher(fileName).matches();
            if (accept) {
                try {
                    retval.add(new URL("jar:" + new File(file.getPath() + "!/" + fileName).toURI().toURL()));
                } catch (MalformedURLException ex) {
                    throw new Error(ex);
                }
            }
        }
        try {
            zf.close();
        } catch (final IOException e1) {
            throw new Error(e1);
        }
        return retval;
    }

    private static Collection<URL> getResourcesFromDirectory(
            final File directory, final Pattern pattern) {
        final ArrayList<URL> retval = new ArrayList<URL>();
        final File[] fileList = directory.listFiles();
        for (final File file : fileList) {
            if (file.isDirectory()) {
                retval.addAll(getResourcesFromDirectory(file, pattern));
            } else {
                try {
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if (accept) {
                        retval.add(new URL("file://" + fileName));
                    }
                } catch (final IOException e) {
                    throw new Error(e);
                }
            }
        }
        return retval;
    }

    private static final Logger LOGGER = Logger.getLogger(DomainXml.class.getName());
}
