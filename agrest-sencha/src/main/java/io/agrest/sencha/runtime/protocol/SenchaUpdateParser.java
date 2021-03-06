package io.agrest.sencha.runtime.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import io.agrest.meta.AgEntity;
import io.agrest.runtime.jackson.IJacksonService;
import io.agrest.runtime.parser.converter.IJsonValueConverterFactory;
import io.agrest.runtime.protocol.EntityUpdateJsonTraverser;
import io.agrest.runtime.protocol.EntityUpdateJsonVisitor;
import io.agrest.runtime.protocol.EntityUpdateParser;
import io.agrest.runtime.semantics.IRelationshipMapper;
import org.apache.cayenne.di.Inject;

import java.util.regex.Pattern;

/**
 * Strips off Sencha-generated temporary IDs from the update data structures.
 * 
 * @since 1.20
 */
public class SenchaUpdateParser extends EntityUpdateParser {

	private static final Pattern DASH_ID_PATTERN = Pattern.compile(".-[\\d]+$");

	private EntityUpdateJsonTraverser senchaEntityUpdateJsonTraverser;

	public SenchaUpdateParser(@Inject IRelationshipMapper relationshipMapper,
							  @Inject IJacksonService jacksonService,
							  @Inject IJsonValueConverterFactory converterFactory) {
		super(relationshipMapper, jacksonService, converterFactory);
		senchaEntityUpdateJsonTraverser = new SenchaEntityJsonTraverser(relationshipMapper, converterFactory);
	}

	protected boolean isTempId(JsonNode value) {
		if (value != null && value.isTextual()) {
			String idString = value.textValue();
			if (DASH_ID_PATTERN.matcher(idString).find()) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected EntityUpdateJsonTraverser entityJsonTraverser() {
		return senchaEntityUpdateJsonTraverser;
	}

	private class SenchaEntityJsonTraverser extends EntityUpdateJsonTraverser {

		public SenchaEntityJsonTraverser(IRelationshipMapper relationshipMapper, IJsonValueConverterFactory converterFactory) {
			super(relationshipMapper, converterFactory);
		}

		@Override
		protected void extractPK(AgEntity<?> entity, EntityUpdateJsonVisitor visitor, JsonNode valueNode) {
			// if PK is a Sencha temporary value, completely ignore it...
			if (!isTempId(valueNode)) {
				super.extractPK(entity, visitor, valueNode);
			}
		}
	}
}
