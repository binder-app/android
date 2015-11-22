package ca.binder.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import ca.binder.remote.Photo;

/**
 * Represents a student whom the current student has "matched" with.
 *
 * @author Mitchell Hentges
 * @since 11/11/2015
 */
public class Match implements Parcelable {


    private final String name;
	private final String bio;
	private final String phone;
	private final String program;
	private final String year;
	private final IPhoto photo;
	private final List<Course> courses;


	public Match(String name, String bio, String phone, String program, String year, IPhoto photo, List<Course> courses) {

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

    /**
     * Constructor to create Match from Parcel
     * Parcel is created in writeToParcel method
     * Allows Match objects to be sent via intents
     * @param in    -The Parcel we use to create the Match
     */
    protected Match(Parcel in) {
        name = in.readString();
        bio = in.readString();
        phone = in.readString();
        program = in.readString();
        year = in.readString();
        photo = new Photo(in.readString());
        if (in.readByte() == 0x01) {    //Signals start of list
            courses = new ArrayList<>();
            in.readList(courses, Course.class.getClassLoader());
        } else {    //List was null, so set list to null
            courses = null;
        }
    }


    /************************************
     * Parcelable method implementations
     * Used for creating and formatting
     * the Parcel, and recreating a Match
     * from the Parcel
     ***********************************/

    /**
     * Class descriptor, used as flag in writeToParcel
     * @return	- Class descriptor
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Main method for creating a Parcel of Match's data
     * @param dest	- The Parcel to be written
     * @param flags	- The Parcel descriptor
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(bio);
        dest.writeString(phone);
        dest.writeString(program);
        dest.writeString(year);
        dest.writeString(photo.base64());
        if (courses == null) {
            dest.writeByte((byte) (0x00));  //Marks end of Parcel if courses is null
        } else {
            dest.writeByte((byte) (0x01));  //Marks start of list
            dest.writeList(courses);
        }
    }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
}