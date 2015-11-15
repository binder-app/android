package ca.binder;

/**
 * Created by stevenlyall on 15-11-14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
		TextView suggestionBioTextView = (TextView) card.findViewById(R.id.suggestionBioTextView);
		ImageView suggestionPhotoImageView = (ImageView) card.findViewById(R.id.suggestionPhotoImageView);

		Suggestion suggestion = contents.get(position);

		suggestionNameTextView.setText(suggestion.getName());
		suggestionProgramTextView.setText(suggestion.getProgram());
		suggestionYearTextView.setText(suggestion.getYear());
		suggestionBioTextView.setText(suggestion.getBio());
		//TODO photo
		//suggestionPhotoImageView.setImageDrawable(suggestion.getPhoto().getDrawable(getContext()));
		return card;
	}

}

