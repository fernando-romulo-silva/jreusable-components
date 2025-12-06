package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.Function;

import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PreCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.PreExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.FindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.PreFindAllFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;

public sealed interface OperationFunction1Args<In, Out> extends Function<In, Out>, OperationFunction
		permits CustomOperation1Args,
		FindAllFunction, PreFindAllFunction,
		CountAllFunction, PreCountAllFunction,
		ExistsAllFunction, PreExistsAllFunction {

}
