
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
public class Comment extends Resource {

	/**
	 * The username (identifier) of the user
	 */
	private final int id;

	private final String username;

	private final int problem;

	private final int thread;

	private final int comment;
	/**
	 * The surname of the user
	 */
	private final String text;

	/**
	* The gender of the user
	*/
	private final String date;

	public Comment(final int id, final String username, final int problem, final int thread, final int comment, final String text, final String date) {
		this.id = id;
		this.username = username;
		this.problem = problem;
		this.thread = thread;
		this.comment = comment;
		this.text = text;
		this.date = date;
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


	public final int getProblem(){
		return problem;
	}

	public final int getComment(){
		return comment;
	}

	/**
	 * Returns the surname of the user.
	 * 
	 * @return the surname of the user.
	 */
	public final int getThread() {
		return thread;
	}

	public final String getText() {
		return text;
	}

	/**
	 * Returns the birth of the user.
	 * 
	 * @return the birth of the user.
	 */
	public final String getDate() {
		return date;
	}



	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("comment");

		jg.writeStartObject();

		jg.writeStringField("username", username);

		jg.writeNumberField("problem", problem);

		jg.writeNumberField("thread", thread);

		jg.writeNumberField("comment", comment);

		jg.writeStringField("date", date);

		jg.writeNumberField("id", id);

		jg.writeStringField("text", text);

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
	public static Comment fromJSON(final InputStream in) throws IOException {

		// the fields read from JSON

		String jUsername = null;
		int jThread = -1;
		int jProblem = -1;
		int jComment = -1;
		int jId = -1;
		String jDate = null;
		String jText = null;

		// the created user
		User u = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "comment".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
				throw new IOException("Unable to parse JSON: no comment object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {

			if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {

				switch (jp.getCurrentName()) {
					case "username":
						jp.nextToken();
						jUsername = jp.getText();
						break;
					case "problem":
						jp.nextToken();
						jProblem = jp.getIntValue();
						break;
					case "thread":
						jp.nextToken();
						jThread = jp.getIntValue();
						break;
					case "comment":
						jp.nextToken();
						jComment = jp.getIntValue();
						break;
					case "id":
						jp.nextToken();
						jId = jp.getIntValue();
						break;
					case "date":
						jp.nextToken();
						jDate = jp.getText();
						break;
					case "text":
						jp.nextToken();
						jText = jp.getText();
						break;

				}
			}
		}

		return new Comment(jId, jUsername, jProblem, jThread, jComment, jText, jDate);
	}
}
