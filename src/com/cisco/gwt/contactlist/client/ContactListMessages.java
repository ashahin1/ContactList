package com.cisco.gwt.contactlist.client;

import com.google.gwt.i18n.client.Messages;

public interface ContactListMessages extends Messages {
	@DefaultMessage("{0} must not be blank")
	String errorBlank(String cField);

	@DefaultMessage("Do you want to remove {0} ?")
	String confirmDelete(String cName);
}
