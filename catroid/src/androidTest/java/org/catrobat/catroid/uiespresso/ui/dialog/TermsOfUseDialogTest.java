/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.uiespresso.ui.dialog;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.uiespresso.testsuites.Cat;
import org.catrobat.catroid.uiespresso.testsuites.Level;
import org.catrobat.catroid.uiespresso.ui.fragment.rvutils.RecyclerViewActions;
import org.catrobat.catroid.uiespresso.util.rules.DontGenerateDefaultProjectActivityInstrumentationRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class TermsOfUseDialogTest {
	private IdlingResource idlingResource;

	@Rule
	public DontGenerateDefaultProjectActivityInstrumentationRule<MainMenuActivity> baseActivityTestRule = new
			DontGenerateDefaultProjectActivityInstrumentationRule<>(MainMenuActivity.class, true, false);

	@Before
	public void setUp() throws Exception {
		baseActivityTestRule.launchActivity(null);

		idlingResource = baseActivityTestRule.getActivity().getIdlingResource();
		IdlingRegistry.getInstance().register(idlingResource);
	}

	@Category({Cat.AppUi.class, Level.Smoke.class})
	@Test
	public void termsOfUseDialogTest() {
		RecyclerViewActions.openOverflowMenu();
		onView(withText(R.string.main_menu_terms_of_use)).perform(click());

		int alertTitleId = baseActivityTestRule.getActivity().getResources()
				.getIdentifier("alertTitle", "id", "android");

		onView(allOf(withId(alertTitleId), isDisplayed()))
				.check(matches(withText(R.string.dialog_terms_of_use_title)));

		onView(withText(R.string.dialog_terms_of_use_info))
				.check(matches(isDisplayed()));

		onView(allOf(withId(R.id.dialog_terms_of_use_text_view_url), isDisplayed()))
				.check(matches(withText(R.string.dialog_terms_of_use_link_text)));

		onView(withText(R.string.ok))
				.perform(click());

		onView(withText(R.string.dialog_terms_of_use_title))
				.check(doesNotExist());
	}

	@After
	public void tearDown() throws Exception {
		IdlingRegistry.getInstance().unregister(idlingResource);
	}
}
