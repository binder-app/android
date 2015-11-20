package ca.binder.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a student whom the current student has "matched" with.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Match implements Serializable{
    private final String name;
	private final String bio;
	private final String phone;
	private final String program;
	private final String year;
	private final IPhoto photo;
	private final List<Course> courses;

	public Match(String name, String bio, String phone, String program, String year, List<Course> courses, IPhoto photo) {

        this.name = name;
		this.bio = bio;
		this.phone = phone;
		this.program = program;
		this.year = year;
		this.photo = photo;
		this.courses = courses;
	}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

	public String getBio() {
		return bio;
	}

    public IPhoto getPhoto() {
        return photo;
    }

	public List<Course> getCourses() {
		return courses;
	}

	public String getProgram() {
		return program;
	}

	public String getYear() {
		return year;
	}
}
