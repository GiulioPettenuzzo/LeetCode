package it.unipd.dei.webapp.resource;

/**
 * Represents the data about an employee.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */

import com.fasterxml.jackson.core.*;

import java.io.*;

public class Hint extends Resource {

	/**
	 * The identifier of the hint
	 */
	private final int id;

	/**
	 * The associated problem to the hint
	 */
	private final int problem;

	/**
	 * The description of the hint
	 */
	private final String description;

	/**
	 * Creates a new hint
	 * 
	 * @param id
	 *            the id of the hint.
	 * @param problem
	 *            the associated problem to the hint.
	 * @param description
	 *            the description of the hint.
	 */
	public Hint(final int id, final int problem, final String description) {
		this.id = id;
		this.problem = problem;
		this.description = description;
	}

	/**
	 * Returns the id of the hint.
	 * 
	 * @return the id of the hint.
	 */
	public final int getId() {
		return id;
	}

	/**
	 * Returns the associated problem to the hint.
	 * 
	 * @return the associated problem to the hint.
	 */
	public final int getProblem() {
		return problem;
	}

	/**
	 * Returns the description of the hint.
	 * 
	 * @return the description of the hint.
	 */
	public final String getDescription() {
		return description;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("hint");

		jg.writeStartObject();

		jg.writeNumberField("id", id);

		jg.writeNumberField("problem", problem);

		jg.writeStringField("description", description);

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
	public static Hint fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		String jdescription = null;
		int jProblem = -1;
		int jId= -1;

		// the created user
		Hint u = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "hint".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no user object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "id":
						jp.nextToken();
						jId = jp.getIntValue();
						break;
					case "problem":
						jp.nextToken();
						jProblem = jp.getIntValue();
						break;
					case "description":
						jp.nextToken();
						jdescription = jp.getText();
						break;

				}
			}
		}

		return new Hint(jId, jProblem, jdescription);
	}
}
