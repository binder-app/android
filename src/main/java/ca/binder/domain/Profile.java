package ca.binder.domain;

import java.util.ArrayList;
import java.util.List;

import ca.binder.android.InternalPhoto;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Profile {
    private final String id;
    private String name;
    private String program;
    private String bio;
    private String phone;
    private String year;
    private InternalPhoto photo;
    private List<Course> courses = new ArrayList<>();

    public Profile(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public IPhoto getPhoto() {
        return photo;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    //todo setting photo
}
