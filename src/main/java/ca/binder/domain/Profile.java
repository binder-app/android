package ca.binder.domain;

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
    private int year;
    private InternalPhoto photo;

    public Profile(String id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public int getYear() {
        return year;
    }

    public IPhoto getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //todo setting photo
}
