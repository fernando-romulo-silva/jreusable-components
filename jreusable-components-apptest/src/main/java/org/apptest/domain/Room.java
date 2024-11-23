package org.apptest.domain;

import java.math.BigDecimal;

import org.reusablecomponents.base.core.domain.AbstractEntity;

public class Room extends AbstractEntity<Long> {

    private Hotel hotel;

    private String code;

    private BigDecimal value = BigDecimal.ZERO;
}
