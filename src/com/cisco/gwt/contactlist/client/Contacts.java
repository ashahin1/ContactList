package com.cisco.gwt.contactlist.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;

public class Contacts implements IGroups {
	private final ArrayList<ContactItem> contacts = new ArrayList<>();
	private ContactListConstants constants = GWT.create(ContactListConstants.class);

	public ArrayList<ContactItem> getContacts() {
		return contacts;
	}

	public void add(List<String> contactData) {
		ContactItem cItem = new ContactItem(contactData.get(0),
				contactData.get(1), Integer.parseInt(contactData.get(2)),
				contactData.get(3), groupsList.indexOf(contactData.get(4)),
				contactData.get(5).equals(constants.yes()) ? true : false);

		contacts.add(cItem);
	}
	
	public void remove(int index){
		contacts.remove(index);
	}

	public void sortByLastName() {
		Collections.sort(contacts, new Comparator<ContactItem>() {

			@Override
			public int compare(ContactItem c1, ContactItem c2) {
				FullName name1 = new FullName();
				FullName name2 = new FullName();

				String[] nameParts = c1.getName().split(" ", 2); //$NON-NLS-1$
				if (nameParts.length > 1)
					name1.setLastName(nameParts[1]);
				name1.setFirstName(nameParts[0]);

				nameParts = c2.getName().split(" ", 2); //$NON-NLS-1$
				if (nameParts.length > 1)
					name2.setLastName(nameParts[1]);
				name2.setFirstName(nameParts[0]);

				int res = name1.getLastName().compareToIgnoreCase(
						name2.getLastName());
				if (res != 0)
					return res;
				return name1.getFirstName().compareToIgnoreCase(
						name2.getFirstName());
			}
		});
	}

	class FullName {
		private String firstName = ""; //$NON-NLS-1$
		private String lastName = ""; //$NON-NLS-1$

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}
}
