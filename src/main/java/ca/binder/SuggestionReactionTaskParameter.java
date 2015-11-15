package ca.binder;

import android.content.Context;

import ca.binder.domain.Suggestion;
import ca.binder.domain.SuggestionReaction;

class SuggestionReactionTaskParameter {
	Context context;
	SuggestionReaction reaction;
	String suggestionId;
	Boolean success;

	SuggestionReactionTaskParameter(Context context, Suggestion suggestion, SuggestionReaction reaction) {
		this.context = context;
		this.reaction = reaction;
		this.suggestionId = suggestion.getId();
		this.success = false;
	}
}