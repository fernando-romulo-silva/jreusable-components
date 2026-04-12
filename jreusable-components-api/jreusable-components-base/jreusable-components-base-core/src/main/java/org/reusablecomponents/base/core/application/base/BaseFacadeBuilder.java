package org.reusablecomponents.base.core.application.base;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.infra.exception.DefaultExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.DefaultSecurityService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.reusablecomponents.base.translation.JavaSEI18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>EntiyBaseFacade</code> builder's class.
 * 
 * The builder is used to construct the base facade, which is the base class for
 * all facades. The builder is used to set the security service, the i18n
 * service and the exception adapter service. If any of these services is not
 * set, the default implementation will be used.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see InterfaceSecurityService
 * @see InterfaceI18nService
 * @see InterfaceExceptionAdapterService
 */
public class BaseFacadeBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFacadeBuilder.class);

	/**
	 * Security service, in case of null, the <code>DefaultSecurityService</code>
	 * will be used.
	 * 
	 * @see InterfaceSecurityService
	 */
	public InterfaceSecurityService securityService;

	/**
	 * Language translator service, in case of null, the
	 * <code>JavaSEI18nService</code> will be used.
	 * 
	 * @see InterfaceI18nService
	 */
	public InterfaceI18nService i18nService;

	/**
	 * Exception adapter service, in case of null, the
	 * <code>DefaultExceptionAdapterService</code> will be used.
	 * 
	 * @see InterfaceExceptionAdapterService
	 */
	public InterfaceExceptionAdapterService exceptionAdapterService;

	/**
	 * Default constructor.
	 * 
	 * The constructor receives a consumer function, which is used to set the
	 * builder attributes. The function is called with the builder instance as
	 * parameter.
	 * 
	 * @param function Consumer function, can't be null, used to set the builder
	 *                 attributes
	 * 
	 * @throws NullPointerException if the function is null
	 * 
	 * @see JavaSEI18nService
	 * @see DefaultSecurityService
	 * @see DefaultExceptionAdapterService
	 */
	public BaseFacadeBuilder(final Consumer<? extends BaseFacadeBuilder> function) {
		LOGGER.atDebug().log("Constructing BaseFacadeBuilder");

		@SuppressWarnings("unchecked")
		final var finalFunction = (Consumer<BaseFacadeBuilder>) function;

		finalFunction.accept(this);

		i18nService = nonNull(i18nService)
				? i18nService
				: new JavaSEI18nService();

		securityService = nonNull(securityService)
				? securityService
				: new DefaultSecurityService();

		exceptionAdapterService = nonNull(exceptionAdapterService)
				? exceptionAdapterService
				: new DefaultExceptionAdapterService();

		LOGGER.atDebug().log(
				"BaseFacadeBuilder constructed: securityService {}, i18nService {}, exceptionAdapterService {}",
				securityService.getClass().getSimpleName(),
				i18nService.getClass().getSimpleName(),
				exceptionAdapterService.getClass().getSimpleName());
	}
}
