
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
public class Problem extends Resource {

	/**
	 * The username (identifier) of the user
	 */
	private final int id;


	private final String username;

	/**
	 * The name of the user
	 */
	private final String title;

	/**
	 * The surname of the user
	 */
	private final String difficulty;

	/**
	 * The email of the user
	 */
	private final String description;


	private final String solution;

	private final String date;


	public Problem(final int id, final String username, final String title, final String difficulty, final String description, final String date, String solution) {
		this.id = id;
		this.username = username;
		this.title = title;
		this.difficulty = difficulty;
		this.description = description;
		this.date = date;
		this.solution = solution;
	}

	/**
	 * Returns the badge number of the user.
	 * 
	 * @return the badge number of the user.
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return the name of the user.
	 */
	public final int getId() {
		return id;
	}


	public final String getTitle(){
		return title;
	}

	/**
	 * Returns the surname of the user.
	 * 
	 * @return the surname of the user.
	 */
	public final String getDifficulty() {
		return difficulty;
	}

		public final String getDescription() {
		return description;
	}

	/**
	 * Returns the birth of the user.
	 * 
	 * @return the birth of the user.
	 */
	public final String getDate() {
		return date;
	}

	public final String getSolution() {
		return solution;
	}


	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("problem");

		jg.writeStartObject();

		jg.writeStringField("username", username);

		jg.writeStringField("description", description);

		jg.writeStringField("title", title);

		jg.writeStringField("date", date);

		jg.writeNumberField("id", id);

		jg.writeStringField("difficulty", difficulty);

		jg.writeStringField("solution", solution);

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
	public static Problem fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		String jUsername = null;
		String jTitle = null;
		String jDescription = null;
		int jId = -1;
		String jDate = null;
		String jDifficulty = null;
		String jSolution = null;
		String jCompany = null;

		// the created user
		User u = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "problem".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no user object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "username":
						jp.nextToken();
						jUsername = jp.getText();
						break;
					case "title":
						jp.nextToken();
						jTitle = jp.getText();
						break;
					case "description":
						jp.nextToken();
						jDescription = jp.getText();
						break;
					case "id":
						jp.nextToken();
						jId = jp.getIntValue();
						break;
					case "date":
						jp.nextToken();
						jDate = jp.getText();
						break;
					case "difficulty":
						jp.nextToken();
						jDifficulty = jp.getText();
						break;
					case "solution":
						jp.nextToken();
						jSolution = jp.getText();
						break;
				}
			}
		}

		return new Problem(jId, jUsername, jTitle, jDifficulty, jDescription, jDate, jSolution);
	}
}
