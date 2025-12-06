package org.reusablecomponents.base.core.infra.util.function.operation;

import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.command.entity.function.delete.DeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.PosDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete.PreDeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.DeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.PosDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.PreDeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.DeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.PosDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.PreDeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.DeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.PosDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.PreDeleteByIdsFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PosSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.PreSaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.SaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.PosSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.PreSaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.SaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.PosUpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.PreUpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.UpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.PosUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.PreUpdateAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.UpdateAllFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.FindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PosFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PreFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.FindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PosFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PreFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PosFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PosFindOneBySpecificationSortedFunction;
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
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.CountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.PosCountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.PreCountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.ExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.PosExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.PreExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.FindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.PosFindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.PreFindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.FindOneBySpecFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.PosFindOneBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.PreFindOneBySpecificationFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation2Args;

public sealed interface OperationFunction2Args<In1, In2, Out> extends BiFunction<In1, In2, Out>, OperationFunction
		permits CustomOperation2Args,
		PreSaveFunction, SaveFunction, PosSaveFunction,
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
		PreExistsByIdFunction, ExistsByIdFunction, PosExistsByIdFunction,
		CountBySpecificationFunction, PosFindOneBySpecificationFunction, ExistsBySpecificationFunction,
		PreCountBySpecificationFunction, FindBySpecificationFunction, PosCountBySpecificationFunction,
		PreExistsBySpecificationFunction, PosExistsBySpecificationFunction, PreFindBySpecificationFunction,
		PosFindBySpecificationFunction, PreFindOneBySpecificationFunction, FindOneBySpecFunction,
		FindAllPagedFunction, PreFindOneSortedFunction, PreFindAllPagedFunction,
		PosFindAllPagedFunction, FindOneSortedFunction, PosFindOneSortedFunction,
		PosFindOneBySpecificationSortedFunction,
		PosFindBySpecificationPagedFunction {
}
