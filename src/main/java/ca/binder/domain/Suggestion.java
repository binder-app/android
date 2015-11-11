package ca.binder.domain;

import com.squareup.okhttp.OkHttpClient;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Suggestion {
    private final String id;
    private final String name;
    private final String program;
    private final String bio;
    private final int year;
    private final IPhoto photo;

    public Suggestion(String id, String name, String program, String bio, int year, IPhoto photo) {
        this.id = id;
        this.name = name;
        this.program = program;
        this.bio = bio;
        this.year = year;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProgram() {
        return program;
    }

    public String getBio() {
        return bio;
    }

    public int getYear() {
        return year;
    }

    public IPhoto getPhoto() {
        return photo;
    }

    public void like(OkHttpClient client) {
        // TODO
    }

    public void unlike(OkHttpClient client) {
        // TODO
    }
}
