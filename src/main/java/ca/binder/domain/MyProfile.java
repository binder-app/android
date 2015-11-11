package ca.binder.domain;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class MyProfile {
    private final String id;
    private final String name;
    private final String program;
    private final String phone;
    private final int year;
    private final IPhoto photo;

    public MyProfile(String id, String name, String program, String phone, int year, IPhoto photo) {
        this.id = id;
        this.name = name;
        this.program = program;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public int getYear() {
        return year;
    }

    public IPhoto getPhoto() {
        return photo;
    }
}
