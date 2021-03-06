package it.unipd.dei.webapp.resource;

/**
 * Represents the data about a category.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
import com.fasterxml.jackson.core.*;

import java.io.*;

public class Category extends Resource{

	/**
	 * The name (identifier) of the company.
	 */
	private final String name;

	/**
	 * Creates a new category.
	 * 
	 * @param name
	 *            the name of the category.
	 */

	public Category(final String name) {
		this.name = name;
	}


	/**
	 * Returns the name of the category.
	 * 
	 * @return the name of the category.
	 */
	public final String getName() {
		return name;
	}


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("category");

		jg.writeStartObject();

		jg.writeStringField("name", name);

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
	public static Category fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		String jName = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "category".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no user object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "name":
						jp.nextToken();
						jName = jp.getText();
						break;
				}
			}
		}

		return new Category(jName);
	}

}