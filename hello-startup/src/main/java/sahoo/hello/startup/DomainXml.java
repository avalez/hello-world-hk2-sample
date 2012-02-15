package sahoo.hello.startup;

import com.sun.enterprise.module.bootstrap.Populator;

import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.config.ConfigParser;
import org.jvnet.hk2.config.DomDocument;

import java.io.File;
import java.io.IOException;
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
    public void run(ConfigParser parser) {
        try {
            URL parent = this.getClass().getResource("../../../");
            LOGGER.info("Looking for domain*.xml files in " + parent);
            Collection<String> resources = getResources(parent, Pattern.compile(".*/domain.*xml"));
            
            if (resources.size() == 0) {
                LOGGER.severe("No config files to load");
            }
            for (String res: resources) {
                URL url = new URL("file://" + res);
                LOGGER.info("Loading " + url);
                DomDocument document = parser.parse(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // http://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory
    private static Collection<String> getResources(final URL element,
            final Pattern pattern) throws URISyntaxException {
        final ArrayList<String> retval = new ArrayList<String>();
        final File file = new File(element.toURI());
        if (file.isDirectory()) {
            retval.addAll(getResourcesFromDirectory(file, pattern));
        } else {
            retval.addAll(getResourcesFromJarFile(file, pattern));
        }
        return retval;
    }

    private static Collection<String> getResourcesFromJarFile(final File file,
            final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<String>();
        ZipFile zf;
        try {
            zf = new ZipFile(file);
        } catch (final ZipException e) {
            throw new Error(e);
        } catch (final IOException e) {
            throw new Error(e);
        }
        final Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = pattern.matcher(fileName).matches();
            if (accept) {
                retval.add(fileName);
            }
        }
        try {
            zf.close();
        } catch (final IOException e1) {
            throw new Error(e1);
        }
        return retval;
    }

    private static Collection<String> getResourcesFromDirectory(
            final File directory, final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<String>();
        final File[] fileList = directory.listFiles();
        for (final File file : fileList) {
            if (file.isDirectory()) {
                retval.addAll(getResourcesFromDirectory(file, pattern));
            } else {
                try {
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if (accept) {
                        retval.add(fileName);
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
