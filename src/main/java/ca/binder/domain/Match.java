package ca.binder.domain;

import java.io.Serializable;

/**
 * Represents a student whom the current student has "matched" with.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Match implements Serializable{
    private final String name;
    private final String phone;

    public Match(String name, String phone) {

        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
