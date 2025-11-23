package org.reusablecomponents.base.core.application.command.entity.function.delete;

import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
* 
*/
@FunctionalInterface
public non-sealed interface DeleteFunction<DeleteEntityIn, DeleteEntityOut>
        extends OperationFunction2Args<DeleteEntityIn, Object[], DeleteEntityOut> {

    public static void main(String[] args) {
        final DeleteFunction<String, String> f = (deleteEntityIn, deleteEntityOut) -> {
            return "null";
        };

        System.out.println(f.getName());
    }

}