package ca.binder.domain;

import ca.binder.remote.RemotePhoto;

import java.util.List;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Suggestion {
    private final String id;
    private final String name;
    private final String program;
    private final String bio;
    private final List<Course> courses;
    private final String year;
    private final IPhoto photo;

    public Suggestion(String id, String name, String program, String bio, String year, List<Course> courses, String photoUrl) {
        this(id, name, program, bio, year, courses, new RemotePhoto(photoUrl));
    }

    public Suggestion(String id, String name, String program, String bio, String year, List<Course> courses, IPhoto photo) {
        this.id = id;
        this.name = name;
        this.program = program;
        this.bio = bio;
        this.courses = courses;
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

    public List<Course> getCourses() {
        return courses;
    }

    public String getYear() {
        return year;
    }

    public IPhoto getPhoto() {
        return photo;
    }


}
