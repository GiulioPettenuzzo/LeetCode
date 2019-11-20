
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
public class User extends Resource {

	/**
	 * The username (identifier) of the user
	 */
	private final String username;

	/**
	 * The name of the user
	 */
	private final String name;

	/**
	 * The surname of the user
	 */
	private final String surname;

	/**
	 * The email of the user
	 */
	private final String email;

	/**
	* The gender of the user
	*/
	private final int gender;

	/**
	 * The profile image of the user
	 */
	private final String profileImage;

	private final String password;

	/**
	* The birth of the user
	*/
	private final String date;

	public User(final String username, final String name, final String surname, final int gender, final String profileImage, final String date, String email, String password) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.profileImage = profileImage;
		this.date = date;
		this.email = email;
		this.password = password;
	}

	public User(final String username){
		this.username = username;
		this.name = null;
		this.surname = null;
		this.gender = -1;
		this.profileImage = null;
		this.date = null;
		this.email = null;
		this.password = null;
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
	public final String getName() {
		return name;
	}

	public final String getPassword(){
		return password;
	}

	/**
	 * Returns the surname of the user.
	 * 
	 * @return the surname of the user.
	 */
	public final String getSurname() {
		return surname;
	}

	/**
	 * Returns the gender of the user.
	 * 
	 * @return the gender of the user.
	 */
	public final int getGender() {
		return gender;
	}

	/**
	 * Returns the imageProfile of the user.
	 * 
	 * @return the imageProfile of the user.
	 */
	public final String getProfileImage() {
		return profileImage;
	}

	/**
	 * Returns the birth of the user.
	 * 
	 * @return the birth of the user.
	 */
	public final String getDate() {
		return date;
	}

	/**
	 * Returns the email of the user.
	 * 
	 * @return the email of the user.
	 */
	public final String getEmail() {
		return email;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("users");

		jg.writeStartObject();

		jg.writeStringField("username", username);

		jg.writeStringField("name", name);

		jg.writeStringField("surname", surname);

		jg.writeNumberField("gender", gender);

		jg.writeStringField("profileImage", profileImage);

		jg.writeStringField("date", date);

		jg.writeStringField("email", email);

		jg.writeStringField("password", password);

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
	public static User fromJSON(final String in) throws IOException {

		// the fields read from JSON
		String jUsername = null;
		String jName = null;
		String jSurname = null;
		int jAge = -1;
		int jGender = -1;
		String jprofileImage = null;
		String jDate = null;
		String jEmail = null;
		String jPassword = null;

		// the created user
		User u = null;


		final JsonParser jp = JSON_FACTORY.createParser(in);

		// while we are not on the start of an element or the element is not
		// a token element, advance to the next element (if any)
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "users".equals(jp.getCurrentName()) == false) {

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
					case "surname":
						jp.nextToken();
						jSurname = jp.getText();
						break;
					case "name":
						jp.nextToken();
						jName = jp.getText();
						break;
					case "gender":
						jp.nextToken();
						jGender = jp.getIntValue();
						break;
					case "profileImage":
						jp.nextToken();
						jprofileImage = jp.getText();
						break;
					case "date":
						jp.nextToken();
						jDate = jp.getText();
						break;
					case "email":
						jp.nextToken();
						jEmail = jp.getText();
						break;
					case "password":
						jp.nextToken();
						jPassword = jp.getText();
						break;

				}
			}
		}

		return new User(jUsername, jName, jSurname, jGender, jprofileImage, jDate, jEmail, jPassword);
	}
}
