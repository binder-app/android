package ca.binder.domain;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stevenlyall on 15-11-17.
 */
public class CourseListManager {

	private static CourseListManager instance;

	private AssetManager assetManager;

	private List<String> courseList;

	private final String LOG_TAG = "CourseListManager";

	private CourseListManager(Context context) {
		courseList = new ArrayList<>();
		assetManager = context.getAssets();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open("courses.txt")));
			String line;
			while ((line = br.readLine()) != null) {
				courseList.add(line); //can be changed to Course array in future if necessary
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i(LOG_TAG, "Loaded " + courseList.size() + " courses");
	}

	public static CourseListManager getInstance(Context context) {
		if (instance == null) {
			instance = new CourseListManager(context);
		}
		return instance;
	}

	public List<String> getCourseList() {
		return courseList;
	}

}
