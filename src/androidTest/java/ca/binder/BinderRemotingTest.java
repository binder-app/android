package ca.binder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.binder.android.NullPhoto;
import ca.binder.domain.Profile;
import ca.binder.domain.ProfileBuilder;
import ca.binder.remote.Photo;
import ca.binder.remote.Server;
import ca.binder.remote.request.DislikeSuggestionRequest;
import ca.binder.remote.request.GetMatchesRequest;
import ca.binder.remote.request.GetSuggestionsRequest;
import ca.binder.remote.request.LikeSuggestionRequest;
import ca.binder.remote.request.UpdateProfileRequest;

public class BinderRemotingTest {

	private final String id = "test_id";
	Server server;
	Profile profile;

	@Before
	public void SetUp() {
		server = new Server(Server.API_LOCATION, id);
		profile = createTestProfile();

	}

	private Profile createTestProfile() {
		Photo photo = new Photo(new NullPhoto().base64());
		return ProfileBuilder.start(id).bio("I am a test.").phone("2501234567").name("Test").year("Year 3").program("Testing").photo(photo).build();
	}

	@Test
	public void testCallAPIUpdateProfileCreatesProfileSuccessfully() {
		Assert.assertTrue((Boolean) new UpdateProfileRequest(profile).request(new Server(Server.API_LOCATION, id)));
	}

	@Test
	public void testCallAPIGetSuggestionsGetsSuggestionsSuccessfully() {
		Object results = new GetSuggestionsRequest().request(server);
		Assert.assertNotSame(results, false);
	}

	@Test
	public void testCallAPILikeSuggestionLikesProfileSuccessfully() {
		Assert.assertTrue((Boolean) new LikeSuggestionRequest("test_id").request(new Server(Server.API_LOCATION, "test_id2")));
	}

	@Test
	public void testCallAPIDisikeDuggestionDislikesProfileSuccessfully() {
		Assert.assertTrue((Boolean) new DislikeSuggestionRequest("test_id").request(new Server(Server.API_LOCATION, "test_id2")));
	}

	@Test
	public void testCallAPIGetMatchesGetsMatchesSuccessfully() {
		Object matches = new GetMatchesRequest().request(server);
		Assert.assertNotSame(matches, false);
	}

}
