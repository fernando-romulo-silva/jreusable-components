package org.apptest.domain;

import java.time.LocalDateTime;

import org.reusablecomponents.base.core.domain.AbstractEntity;

public class Reservation extends AbstractEntity<String> {

    private Guest guest;

    private Room room;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

}
