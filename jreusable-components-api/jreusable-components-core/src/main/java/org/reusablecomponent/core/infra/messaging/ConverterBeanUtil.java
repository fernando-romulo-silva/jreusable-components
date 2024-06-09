package org.reusablecomponent.core.infra.messaging;

//import static com.fatboyindustrial.gsonjavatime.Converters.registerAll;

import org.apache.commons.lang3.StringUtils;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

public class ConverterBeanUtil {

//    private static Gson JSON_CONVERTER = registerAll(new GsonBuilder()).create();
    
    public static <T> String converterBeanToJSon(final T object, final Class<T> clazz) {
	
//	final var json = JSON_CONVERTER.toJson(object);
	
	return StringUtils.EMPTY;
    }
}
