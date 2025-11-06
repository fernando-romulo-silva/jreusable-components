package org.reusablecomponents.base.core.infra.util.function.operation;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.ErrorDeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.ErrorSaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.ErrorUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.ErrorUpdateFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ErrorExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.ErrorFindByIdFunction;

public sealed interface OperationFunction3Args<In1, In2, In3, Out>
		extends TriFunction<In1, In2, In3, Out>, OperationFunction
		permits ErrorSaveFunction, ErrorSaveAllFunction,
		ErrorUpdateFunction, ErrorUpdateAllFunction,
		ErrorDeleteFunction, ErrorDeleteAllFunction,
		ErrorDeleteByIdFunction, ErrorDeleteByIdsFunction,
		ErrorFindByIdFunction, ErrorExistsByIdFunction {

}
