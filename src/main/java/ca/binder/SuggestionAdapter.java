package ca.binder;

/**
 * Created by stevenlyall on 15-11-14.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.binder.domain.Course;
import ca.binder.domain.Suggestion;

/**
 * Custom adapter inflates card layouts and places suggestion data in card view
 */
class SuggestionAdapter extends ArrayAdapter<Suggestion> {

	List<Suggestion> contents;

	public SuggestionAdapter(Context context, int resource, List<Suggestion> objects) {
		super(context, resource, objects);
		contents = objects;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View card = inflater.inflate(R.layout.suggestion_card_view, parent, false);

		TextView suggestionNameTextView = (TextView) card.findViewById(R.id.suggestionNameTextView);
		TextView suggestionProgramTextView = (TextView) card.findViewById(R.id.suggestionProgramTextView);
		TextView suggestionYearTextView = (TextView) card.findViewById(R.id.suggestionYearTextView);
		ListView suggestionCoursesListView = (ListView) card.findViewById(R.id.suggestionCoursesListView);
		ImageView suggestionPhotoImageView = (ImageView) card.findViewById(R.id.suggestionPhotoImageView);

		final Suggestion suggestion = contents.get(position);

		suggestionNameTextView.setText(suggestion.getName());
		suggestionProgramTextView.setText(suggestion.getProgram());
		suggestionYearTextView.setText(suggestion.getYear());

		//course list
		List<String> courseNames = new ArrayList<>();
		for (Course c : suggestion.getCourses()) {
			courseNames.add(c.getName());
		}
		suggestionCoursesListView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.course_item_view, R.id.courseNameTextView, courseNames));

		//TODO photo
		//suggestionPhotoImageView.setImageDrawable(suggestion.getPhoto().drawable(getContext()));

		// info button shows bio in modal
		ImageView infoButton = (ImageView) card.findViewById(R.id.infoButtonImageView);
		infoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog dialog;
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				dialog = builder.setMessage(suggestion.getBio()).setCancelable(false).setPositiveButton("Close", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).create();
				dialog.show();
			}
		});
		return card;
	}

}

