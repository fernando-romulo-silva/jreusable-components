package org.reusablecomponents.base.core.infra.util.function.operation;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.ErrorDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.ErrorDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.ErrorDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.ErrorDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.ErrorSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.ErrorSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.ErrorUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.ErrorUpdateFunction;
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

public sealed interface OperationFunction3Args<In1, In2, In3, Out>
		extends TriFunction<In1, In2, In3, Out>, OperationFunction
		permits ErrorSaveFunction, ErrorSaveAllFunction,
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
