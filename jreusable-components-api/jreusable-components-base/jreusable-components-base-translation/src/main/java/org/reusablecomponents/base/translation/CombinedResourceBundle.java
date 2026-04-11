package org.reusablecomponents.base.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.ObjectUtils;

/**
 * A ResourceBundle that combines multiple resource bundles into one.
 * It loads the specified resource bundles and merges their key-value pairs.
 * If there are duplicate keys, the value from the last loaded bundle will be
 * used.
 */
public class CombinedResourceBundle extends ResourceBundle {

    private Map<String, String> combinedResources = new HashMap<>();
    private List<String> bundleNames;
    private Locale locale;

    /**
     * Constructs a CombinedResourceBundle with the specified bundle names and
     * locale.
     *
     * @param bundleNames the list of resource bundle names to combine, in the order
     *                    they should be loaded, can't be null or empty
     * @param locale      the locale for which to load the resource bundles, can't
     *                    be null
     */
    public CombinedResourceBundle(final List<String> bundleNames, final Locale locale) {
        if (ObjectUtils.isEmpty(bundleNames)) {
            throw new IllegalArgumentException("bundleNames can't be null or empty");
        }
        if (ObjectUtils.isEmpty(locale)) {
            throw new IllegalArgumentException("locale can't be null");
        }
        this.bundleNames = bundleNames;
        this.locale = locale;
    }

    /**
     * Loads the resource bundles specified in the constructor and combines their
     * key-value pairs into a single map. If there are duplicate keys, the value
     * from the last loaded bundle will be used.
     */
    public void load() {
        bundleNames.forEach(bundleName -> {
            final var bundle = ResourceBundle.getBundle(bundleName, locale);
            final var keysEnumeration = bundle.getKeys();
            final var keysList = Collections.list(keysEnumeration);
            keysList.forEach(key -> combinedResources.put(key, bundle.getString(key)));
        });
    }

    @Override
    public Object handleGetObject(String key) {
        return combinedResources.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(combinedResources.keySet());
    }
}
