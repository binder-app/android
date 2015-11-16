package ca.binder.domain;

import android.content.Context;

import ca.binder.android.DeviceInfo;
import ca.binder.android.InternalPhoto;

/**
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class ProfileBuilder {

    private final Profile profile;

    public ProfileBuilder(Profile profile) {
        this.profile = profile;
    }

    public static ProfileBuilder start(Context context) {
        String id = DeviceInfo.deviceId(context);
        return new ProfileBuilder(new Profile(id));
    }

    public static ProfileBuilder start(String id) {
        return new ProfileBuilder(new Profile(id));
    }

    public ProfileBuilder name(String name) {
        profile.setName(name);
        return this;
    }

    public ProfileBuilder program(String program) {
        profile.setProgram(program);
        return this;
    }

    public ProfileBuilder bio(String bio) {
        profile.setBio(bio);
        return this;
    }

    public ProfileBuilder phone(String phone) {
        profile.setPhone(phone);
        return this;
    }

    public ProfileBuilder year(String year) {
        profile.setYear(year);
        return this;
    }

    public ProfileBuilder photo(InternalPhoto photo) {
        profile.setPhoto(photo);
        return this;
    }

    public ProfileBuilder course(Course course) {
        profile.addCourse(course);
        return this;
    }

    public Profile build() {
        return profile;
    }
}
