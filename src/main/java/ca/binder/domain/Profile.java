package ca.binder.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.ArraySet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.binder.remote.Photo;

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
    private Photo photo;
    private Set<Course> courses = new HashSet<>();

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

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public void save(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> courseSet = new HashSet<>();
        for (Course course : courses) {
            courseSet.add(course.getName());
        }

        preferences.edit()
                .putString("profile_id", id)
                .putString("profile_bio", bio)
                .putString("profile_phone", phone)
                .putString("profile_program", program)
                .putString("profile_year", year)
                .putString("profile_name", name)
                .putString("profile_photo", photo.base64())
                .putStringSet("profile_courses", courseSet)
                .apply();

    }

    /**
     * Returns a profile from persistence, or null if it hasn't been saved before
     * @param context Android context
     * @return Profile from stored data, or null if no data exists
     */
    public static Profile load(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!preferences.contains("profile_id")) {
            return null;
        }

        ProfileBuilder builder = ProfileBuilder.start(preferences.getString("profile_id", ""))
                .bio(fromPref(preferences, "profile_bio"))
                .phone(fromPref(preferences, "profile_phone"))
                .year(fromPref(preferences, "profile_year"))
                .name(fromPref(preferences, "profile_name"))
                .program(fromPref(preferences, "profile_program"))
                .photo(new Photo(fromPref(preferences, "profile_photo")));
        for (String courseName : preferences.getStringSet("profile_courses", new HashSet<String>())) {
            builder = builder.course(new Course(courseName));
        }

        return builder.build();
    }

    private static String fromPref(SharedPreferences preferences, String key) {
        return preferences.getString(key, "");
    }
}
