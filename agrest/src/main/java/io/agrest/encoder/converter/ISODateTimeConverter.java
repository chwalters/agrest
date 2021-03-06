package io.agrest.encoder.converter;

import java.time.Instant;
import java.util.Date;

import static io.agrest.encoder.DateTimeFormatters.isoLocalDateTime;

public class ISODateTimeConverter extends AbstractConverter {

	private static final StringConverter instance = new ISODateTimeConverter();

	public static StringConverter converter() {
		return instance;
	}

	private ISODateTimeConverter() {
	}

	@Override
	protected String asStringNonNull(Object object) {
		Date date = (Date) object;
		return isoLocalDateTime().format(Instant.ofEpochMilli(date.getTime()));
	}
}
