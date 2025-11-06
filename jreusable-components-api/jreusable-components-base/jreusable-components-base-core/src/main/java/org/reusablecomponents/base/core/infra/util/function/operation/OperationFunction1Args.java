package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.Function;

import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PreCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.PreExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.FindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.PreFindAllFunction;

public sealed interface OperationFunction1Args<In, Out> extends Function<In, Out>, OperationFunction
		permits FindAllFunction, PreFindAllFunction,
		CountAllFunction, PreCountAllFunction,
		ExistsAllFunction, PreExistsAllFunction {

}
