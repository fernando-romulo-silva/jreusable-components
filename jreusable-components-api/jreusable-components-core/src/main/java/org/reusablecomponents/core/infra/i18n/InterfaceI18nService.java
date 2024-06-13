package org.reusablecomponents.core.infra.i18n;

/**
 * 
 * Java SE
 * <pre>
 *    final InterfaceI18nService i18n = (code, params)-> {
 *       
 *       // Java SE Locale
 *	 final var locale = Locale.getDefault();
 *	 final var resourceBundle = ResourceBundle.getBundle("messages", locale);
 *       
 *       // Java SE ResourceBundle service    
 *       final var rb = ResourceBundle.getBundle("messages", locale);
 *    
 *       return messageSource.getMessage(code, params, locale);
 *    }
 * </pre>
 * 
 * 
 * Spring Web example:
 * <pre>
 *    final InterfaceI18nService i18n = (code, params)-> {
 *       
 *       // Spring ResourceBundle service    
 *       final var messageSource = BeanUtils.getBeanFrom(MessageSource.class);
 *       
 *       // Spring web Locale service
 *       final var locale = LocaleContextHolder.getLocale(); 
 *    
 *       return messageSource.getMessage(code, params, locale);
 *    }
 * </pre>
 */
@FunctionalInterface
public interface InterfaceI18nService {

    /**
     * @param code   The message code to locate it on properties files.
     * @param params The message's values.
     * @return The message translated.
     */
    String translate(final String code, final Object... params);
}
