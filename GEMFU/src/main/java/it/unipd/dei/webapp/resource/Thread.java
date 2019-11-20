package it.unipd.dei.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Date;

public class Thread extends Resource{

    private String title;
    private String user;
    private String text;
    private String date;
    private int id;
    private int problemId;
    private int views;

    /*
     default constructor
     */
    public Thread(String user, String title, String text, int problemId){
        this.user = user;
        this.title = title;
        this.text = text;
        this.problemId = problemId;
    }

    /*
     constructor for json parce
     */
    public Thread(String title, String text, String user, String date, int problemId){
        this.title = title;
        this.text = text;
        this.user = user;
        this.date = date;
        this.problemId = problemId;
    }

    public Thread(int id, String title, String text, String user, String date, int problemId, int views){
        this.id = id;
        this.title = title;
        this.text = text;
        this.user = user;
        this.date = date;
        this.problemId = problemId;
        this.views = views;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getDate(){
        return date;
    }

    public int getProblemId(){
        return problemId;
    }

    public void setProblemId(){
        this.problemId =  problemId;
    }

    public int getViews(){
        return views;
    }

    public void setViews(int views){
        this.views = views;
    }


    @Override
    public void toJSON(OutputStream out) throws IOException {
        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("thread");

        jg.writeStartObject();

        jg.writeNumberField("id",id);

        jg.writeStringField("title", title);

        jg.writeStringField("text", text);

        jg.writeStringField("user", user);

        jg.writeStringField("date", date);

        jg.writeNumberField("problem-id", problemId);

        jg.writeNumberField("views",views);

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
    public static Thread fromJSON(final InputStream in) throws IOException {

        // the fields read from JSON

        if(in==null){
            throw new NullPointerException("Unable to parse JSON: no user object found.");
        }

        int jID = -1;
        String jTitle = null;
        String jText = null;
        String jUser = null;
        String jDate = null;
        int jProblemId = -1;
        int jViews = -1;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // while we are not on the start of an element or the element is not
        // a token element, advance to the next element (if any)
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "thread".equals(jp.getCurrentName())==false) {

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
                        jID = jp.getIntValue();
                        break;
                    case "title":
                        jp.nextToken();
                        jTitle = jp.getText();
                        break;
                    case "text":
                        jp.nextToken();
                        jText = jp.getText();
                        break;
                    case "user":
                        jp.nextToken();
                        jUser = jp.getText();
                        break;
                    case "date":
                        jp.nextToken();
                        jDate = jp.getText();
                        break;
                    case "problem-id":
                        jp.nextToken();
                        jProblemId = jp.getIntValue();
                        break;
                    case "views":
                        jp.nextToken();
                        jViews = jp.getIntValue();
                        break;
                }
            }
        }

        return new Thread(jID,jTitle,jText,jUser,jDate,jProblemId,jViews);
    }
}
