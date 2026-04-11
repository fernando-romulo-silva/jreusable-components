package org.reusablecomponents.base.translation;

/**
 * 
 * Interface for internationalization (i18n) services. It provides a method to
 * translate messages based on a code and parameters.
 * 
 * Java SE
 * 
 * <pre>
 *    final InterfaceI18nService i18n = (code, params)-> {
 *       
 *       // Java SE Locale
 *	     final var locale = Locale.getDefault();
 *	     final var resourceBundle = ResourceBundle.getBundle("messages", locale);
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
 * 
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
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see java.util.ResourceBundle
 */
@FunctionalInterface
public interface InterfaceI18nService {

    /**
     * Translates a message based on the provided code and parameters. The
     * implementation should locate the message in the appropriate properties files
     * and return the translated message.
     * 
     * @param code   The message code to locate it on properties files.
     * @param params The message's values.
     * @return The message translated.
     */
    String translate(final String code, final Object... params);
}
