
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
public class Submission extends Resource {

	/**
	 * The username (identifier) of the user
	 */
	private final String username;

	private final int problem;

	private final String title;

	private final String text;

	private final String date;

	private final int valid;

	private final int id;

	public Submission(final String username, final int problem, final String title, final String text, final String date, int valid, int id) {
		this.username = username;
		this.problem = problem;
		this.title = title;
		this.date = date;
		this.text = text;
		this.valid = valid;
		this.id = id;
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
	public final int getProblem() {
		return problem;
	}

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
	public final String getText() {
		return text;
	}

	/**
	 * Returns the gender of the user.
	 * 
	 * @return the gender of the user.
	 */
	public final String getDate() {
		return date;
	}

	public final int getValid()
	{
		return valid;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("submission");

		jg.writeStartObject();

		jg.writeStringField("username", username);

		jg.writeNumberField("problem", problem);

		jg.writeStringField("title", title);

		jg.writeStringField("text", text);

		jg.writeNumberField("valid", valid);

		jg.writeStringField("date", date);

		jg.writeNumberField("id", id);

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
	public static Submission fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		String jUsername = null;
		String jTitle = null;
		String jText = null;
		int jValid = -1;
		String jDate = null;
		int jProblem = -1;
		int jId= -1;

		// the created user
		Submission u = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "submission".equals(jp.getCurrentName()) == false) {

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
					case "text":
						jp.nextToken();
						jText = jp.getText();
						break;
					case "problem":
						jp.nextToken();
						jProblem = jp.getIntValue();
						break;
					case "date":
						jp.nextToken();
						jDate = jp.getText();
						break;
					case "valid":
						jp.nextToken();
						jValid = jp.getIntValue();
						break;
					case "id":
						jp.nextToken();
						jId = jp.getIntValue();
						break;

				}
			}
		}

		return new Submission(jUsername, jProblem ,jTitle, jText, jDate, jValid, jId);
	}


}
