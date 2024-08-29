package org.reusablecomponents.base.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class CombinedResourceBundle extends ResourceBundle {

    private Map<String, String> combinedResources = new HashMap<>();
    private List<String> bundleNames;
    private Locale locale;

    public CombinedResourceBundle(List<String> bundleNames, Locale locale) {
        this.bundleNames = bundleNames;
        this.locale = locale;
    }

    public void load() {
        bundleNames.forEach(bundleName -> {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
            Enumeration<String> keysEnumeration = bundle.getKeys();
            ArrayList<String> keysList = Collections.list(keysEnumeration);
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
