package org.reusablecomponents.messaging.event;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * An immutable object represents a system event, like a command (saveEntity,
 * etc.) or a query (findById, etc.).
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 */
public class Event {

	private final String id;

	@Valid
	@NotNull
	private final String origin;

	@Valid
	@NotNull
	private final EventStatus status;

	@Valid
	@NotNull
	private final When when;

	@Valid
	@NotNull
	private final Who who;

	@Valid
	@NotNull
	private final What what;

	@Valid
	@NotNull
	private final Where where;

	@Valid
	@NotNull
	private final Why why;

	/**
	 * Private constructor to enforce the use of the Builder pattern.
	 * 
	 * @param builder The builder instance.
	 */
	private Event(final Builder builder) {
		this.id = UUID.randomUUID().toString();
		this.origin = builder.origin;
		this.status = builder.status;
		this.when = builder.when;
		this.who = builder.who;
		this.what = builder.what;
		this.where = builder.where;
		this.why = builder.why;
	}

	public String getId() {
		return id;
	}

	public String getOrigin() {
		return origin;
	}

	public EventStatus getStatus() {
		return status;
	}

	public When getWhen() {
		return when;
	}

	public Who getWho() {
		return who;
	}

	public What getWhat() {
		return what;
	}

	public Where getWhere() {
		return where;
	}

	public Why getWhy() {
		return why;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (Objects.isNull(obj)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof Event other) {
			return Objects.equals(id, other.id);
		}
		return false;
	}

	/**
	 * Builder class for constructing Event instances.
	 */
	public static class Builder {

		/**
		 * Event id origin, an event was triggered by another event.
		 */
		public String origin;

		/**
		 * Event status, the status of the event: success, failure, etc.
		 */
		public EventStatus status;

		/**
		 * Event when: date, time, and zone the event occurred.
		 */
		public When when;

		/**
		 * Event who.
		 */
		public Who who;

		/**
		 * Event what.
		 */
		public What what;

		/**
		 * Event where.
		 */
		public Where where;

		/**
		 * Event why.
		 */
		public Why why;

		/**
		 * Method to set builder attributes using a consumer function.
		 * 
		 * @param function The consumer function to set attributes.
		 * @return The builder instance.
		 */
		public Builder with(final Consumer<Builder> function) {
			function.accept(this);
			return this;
		}

		/**
		 * Builds the Event instance.
		 * 
		 * @return The constructed Event instance.
		 */
		public Event build() {
			if (Objects.isNull(status)) {
				throw new IllegalArgumentException("The event attribute 'status' is required");
			}

			if (Objects.isNull(when)) {
				throw new IllegalArgumentException("The event attribute 'when' is required");
			}

			if (Objects.isNull(who)) {
				throw new IllegalArgumentException("The event attribute 'who' is required");
			}

			if (Objects.isNull(what)) {
				throw new IllegalArgumentException("The event attribute 'what' is required");
			}

			if (Objects.isNull(where)) {
				throw new IllegalArgumentException("The event attribute 'where' is required");
			}

			if (Objects.isNull(why)) {
				throw new IllegalArgumentException("The event attribute 'why' is required");
			}

			return new Event(this);
		}
	}
}
