package org.reusablecomponents.base.core.infra.util.function.operation;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.ErrorDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.ErrorDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.ErrorDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.ErrorDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.ErrorSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.ErrorUpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.ErrorUpdateAllFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.ErrorFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.ErrorFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.FindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PreFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.FindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PreFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ErrorExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.ErrorFindByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.ErrorCountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.ErrorExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.ErrorFindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.ErrorFindOneBySpecificationFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation3Args;

/**
 * OperationFunction3Args is a functional interface that represents an operation
 * function with three arguments in the application.
 * 
 * It extends the TriFunction interface and the OperationFunction interface,
 * providing additional methods for describing the operation, controlling its
 * execution, and handling exceptions.
 * 
 * This interface can be used as a base for creating specific operation function
 * implementations that require three arguments like save, update, delete, find
 * by specification paged, and find one by specification sorted operations.
 * 
 * @param <In1> The type of the first input to the function
 * @param <In2> The type of the second input to the function
 * @param <In3> The type of the third input to the function
 * @param <Out> The type of the result of the function
 * 
 * @see OperationFunction
 * @see TriFunction
 */
public sealed interface OperationFunction3Args<In1, In2, In3, Out>
		extends TriFunction<In1, In2, In3, Out>, OperationFunction
		permits CustomOperation3Args,
		ErrorSaveFunction, ErrorSaveAllFunction,
		ErrorUpdateFunction, ErrorUpdateAllFunction,
		ErrorDeleteFunction, ErrorDeleteAllFunction,
		ErrorDeleteByIdFunction, ErrorDeleteByIdsFunction,
		ErrorFindByIdFunction, ErrorExistsByIdFunction,
		ErrorCountBySpecificationFunction, ErrorExistsBySpecificationFunction,
		ErrorFindBySpecificationFunction, ErrorFindOneBySpecificationFunction,
		ErrorFindAllPagedFunction, ErrorFindOneSortedFunction,
		PreFindBySpecificationPagedFunction, FindBySpecificationPagedFunction,
		FindOneBySpecificationSortedFunction, PreFindOneBySpecificationSortedFunction {
}
