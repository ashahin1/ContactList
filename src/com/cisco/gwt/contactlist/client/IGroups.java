package com.cisco.gwt.contactlist.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;

public interface IGroups {
	ContactListConstants constants = GWT.create(ContactListConstants.class);
	static final List<String> groupsList = Arrays.asList(
			constants.engineering(), constants.finance(),
			constants.frontOffice(), constants.iT(), constants.management(),
			constants.marketing(), constants.sales());
}
