package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.DeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PosDeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.function.operation.delete.PreDeleteFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PosSaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PosSaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PreSaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.PreSaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.SaveAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.save.SaveFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PosUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PosUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PreUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.PreUpdateFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.UpdateAllFunction;
import org.reusablecomponents.base.core.application.command.function.operation.update.UpdateFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.ErrorCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PosCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ErrorExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.PosExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.PosExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.PreExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.ErrorFindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.PosFindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.FindByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.PosFindByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.PreFindByIdFunction;

public sealed interface OperationFunction2Args<In1, In2, Out> extends BiFunction<In1, In2, Out>, OperationFunction
		permits PreSaveFunction, SaveFunction, PosSaveFunction,
		PreSaveAllFunction, SaveAllFunction, PosSaveAllFunction,
		PreUpdateFunction, UpdateFunction, PosUpdateFunction,
		PreUpdateAllFunction, UpdateAllFunction, PosUpdateAllFunction,
		PreDeleteFunction, DeleteFunction, PosDeleteFunction,
		PreDeleteAllFunction, DeleteAllFunction, PosDeleteAllFunction,
		PreDeleteByIdFunction, DeleteByIdFunction, PosDeleteByIdFunction,
		PreDeleteByIdsFunction, DeleteByIdsFunction, PosDeleteByIdsFunction,
		PreFindByIdFunction, FindByIdFunction, PosFindByIdFunction,
		PosFindAllFunction, ErrorFindAllFunction, ErrorCountAllFunction,
		PosCountAllFunction, PosExistsAllFunction, ErrorExistsAllFunction,
		PreExistsByIdFunction, ExistsByIdFunction, PosExistsByIdFunction {
}
