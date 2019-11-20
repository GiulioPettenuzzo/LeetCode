package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Vote extends Resource {

    private int problemID;
    private int threadID;
    private String username;
    private int vote;

    public Vote(String username, int problemID, int threadID, int vote) {
        this.username = username;
        this.problemID = problemID;
        this.threadID = threadID;
        this.vote = vote;
    }

    public void setProblemID(int problemID) {
        this.problemID = problemID;
    }

    public int getProblemID() {
        return problemID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getVote() {
        return vote;
    }

    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("vote");

        jg.writeStartObject();

        jg.writeStringField("username", username);

        jg.writeNumberField("problem-id", problemID);

        jg.writeNumberField("thread-id", threadID);

        jg.writeNumberField("like", vote);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    public static Vote fromJSON(final ServletInputStream in) throws IOException {
        if (in == null) {
            throw new NullPointerException("Unable to parse JSON: no user object found.");
        }

        String jUsername = null;
        int jProblemId = -1;
        int jThreadId = -1;
        int jVote = 0;


        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "vote".equals(jp.getCurrentName()) == false) {

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
                    case "problem-id":
                        jp.nextToken();
                        jProblemId = jp.getIntValue();
                        break;
                    case "thread-id":
                        jp.nextToken();
                        jThreadId = jp.getIntValue();
                        break;
                    case "like":
                        jp.nextToken();
                        jVote = jp.getIntValue();
                        break;
                }
            }
        }

        return new Vote(jUsername, jProblemId, jThreadId, jVote);
    }
}
