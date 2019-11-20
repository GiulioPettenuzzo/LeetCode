
package it.unipd.dei.webapp.resource;


import com.fasterxml.jackson.core.*;

import java.io.*;

/**
 * Represents the data about an user.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public class ProbCat extends Resource {

	/**
	 * The username (identifier) of the user
	 */
	private final int problem;
	private final String category;


	public ProbCat(final int id, final String category) {
		this.problem = id;
		this.category = category;
	}

	/**
	 * Returns the badge number of the user.
	 * 
	 * @return the badge number of the user.
	 */
	public final String getCategory() {
		return category;
	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return the name of the user.
	 */
	public final int getProblem() {
		return problem;
	}


	
	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("probcat");

		jg.writeStartObject();

		jg.writeStringField("category", category);

		jg.writeNumberField("problem", problem);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}

	/**
	 * Creates a {@code user} from its JSON representation.
	 *
	 * @param in the String containing the JSON document.
	 *
	 * @return the {@code user} created from the JSON representation.
	 *
	 * @throws IOException if something goes wrong while parsing.
	 */
	public static ProbCat fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		int jProblem = -1;
		String jCategory = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "probcat".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no user object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "category":
						jp.nextToken();
						jCategory = jp.getText();
						break;
					case "problem":
						jp.nextToken();
						jProblem = jp.getIntValue();
						break;
				}
			}
		}

		return new ProbCat(jProblem, jCategory);
	}
}
