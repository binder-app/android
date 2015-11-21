package ca.binder.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
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

	private final String COURSE_LIST_DELIMITER = "&";

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

	/**
	 * Create Match from Parcel
	 * 0: name, 1: bio, 2: phone, 3: program, 4: year, 5: photo(base64), 6: courses
	 * @param source	- Parcel that we will use to pass data
	 */
	public Match(Parcel source) {
		String[] data = new String[7];	//Size of String[] contained in source
		source.writeStringArray(data);

		this.name = data[0];
		this.bio = data[1];
		this.phone = data[2];
		this.program = data[3];
		this.year = data[4];
		this.photo = new Photo(data[5]);
		this.courses = new ArrayList<>();

		//Unpackaging Course list, see writeToParcel for packaging protocol
		String[] courseStringList = data[6].split(COURSE_LIST_DELIMITER);
		for(String s : courseStringList) {
			this.courses.add(new Course(s));
		}
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
		//Creating formatted string for parceling Course objects as Strings
		//To be remade after being un-parcelled using Course(String) constructor
		String courseListString = "";
		for(Course c : this.courses) {
			courseListString += c.getName() + COURSE_LIST_DELIMITER;
		}

		dest.writeStringArray(new String[]{ this.name, this.bio, this.phone, this.program,
			this.year, this.photo.base64(), courseListString});
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Match createFromParcel(Parcel source) {
			return new Match(source);
		}

		@Override
		public Match[] newArray(int size) {
			return new Match[size];
		}
	};
}
