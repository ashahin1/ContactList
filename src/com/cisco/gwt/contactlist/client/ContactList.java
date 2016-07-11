package com.cisco.gwt.contactlist.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ContactList implements EntryPoint, IGroups {
	private ContactListConstants constants = GWT
			.create(ContactListConstants.class);
	private ContactListMessages messages = GWT
			.create(ContactListMessages.class);

	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable contactsFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private Label addContactLabel = new Label(constants.contactList());
	private Button addContactButton = new Button(constants.addContact());

	private VerticalPanel editPanel = new VerticalPanel();
	private Label nameLabel = new Label(constants.name());
	private TextBox nameTextBox = new TextBox();
	private Label jobTitleLabel = new Label(constants.jobTitle());
	private TextBox jobTitleTextBox = new TextBox();
	private Label ageLabel = new Label(constants.age());
	private ListBox ageListBox = new ListBox();
	private Label nicknameLabel = new Label(constants.nickname());
	private TextBox nicknameTextBox = new TextBox();
	private Label groupLabel = new Label(constants.group());
	private ListBox groupListBox = new ListBox();
	private CheckBox mangerCheckBox = new CheckBox(constants.manager());
	private HorizontalPanel addCancelPanel = new HorizontalPanel();
	private Button addEditButton = new Button(constants.add());
	private Button cancelButton = new Button(constants.cancel());

	private final DialogBox addEditDialogBox = new DialogBox();

	private Contacts contacts = new Contacts();

	private int editIndex = -1;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		Window.setTitle(constants.contactList());
		RootPanel.get("appTitle").add(new Label(constants.contactList())); //$NON-NLS-1$
		// Create table for Contacts data.
		createTableHeader();
		// Assemble Add Contact panel.
		assmbleAddContactPanel();
		// Assemble Add Cancel panel.
		assembleAddCancelPanel();
		// Assemble Main panel.
		assembleMainPanel();
		// Assemble Edit panel.
		assembleEditPanel();
		// Associate the Main panel with the HTML host page.
		RootPanel.get("contactList").add(mainPanel); //$NON-NLS-1$

		addContactButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editIndex = -1;
				clearDialogData();
				addEditDialogBox.setText(constants.addContact());
				addEditButton.setText(constants.add());
				addEditDialogBox.center();
				addContactButton.setEnabled(false);
				nameTextBox.setFocus(true);
			}
		});

		addClickHandler(contactsFlexTable);
		// Create the popup dialog box
		createAddEditPopupDialogBox();

		addPresetContacts();
	}

	protected void clearDialogData() {
		nameTextBox.setText(""); //$NON-NLS-1$
		jobTitleTextBox.setText(""); //$NON-NLS-1$
		ageListBox.setSelectedIndex(0);
		nicknameTextBox.setText(""); //$NON-NLS-1$
		groupListBox.setSelectedIndex(0);
		mangerCheckBox.setValue(false);
	}

	private void addPresetContacts() {
		addContactToTable(Arrays.asList("Xavier Bigalow", "Team Lead", "28", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"Old Timer", constants.engineering(), constants.yes())); //$NON-NLS-1$
		addContactToTable(Arrays.asList("Judy Cormac", "President", "52", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"The Boss", constants.management(), constants.yes())); //$NON-NLS-1$
		addContactToTable(Arrays.asList("Joe Andretti", "Receptionist", "21", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"Joe", constants.frontOffice(), constants.no())); //$NON-NLS-1$
	}

	private void createAddEditPopupDialogBox() {
		addEditDialogBox.setText(constants.addContact());
		addEditDialogBox.setAnimationEnabled(true);
		addEditDialogBox.setGlassEnabled(true);
		addEditDialogBox.addStyleName("addEditDialogBox"); //$NON-NLS-1$
		//addEditDialogBox.setWidth("400px"); //$NON-NLS-1$

		// We can set the id of a widget by accessing its Element
		cancelButton.getElement().setId("cancelButton"); //$NON-NLS-1$
		addEditButton.getElement().setId("editButton"); //$NON-NLS-1$

		addEditDialogBox.setWidget(editPanel);

		// Add a handler to close the DialogBox
		addEditButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (checkIfValidData()) {
					if (editIndex != -1)
						contacts.remove(editIndex);
					addContactToTable(getContactData());
					addEditDialogBox.hide();
					addContactButton.setEnabled(true);
				} else {
					Window.alert(messages.errorBlank(constants.name())
							+ "\n" + messages.errorBlank(constants.jobTitle())); //$NON-NLS-1$
				}
			}
		});

		// Add a handler to close the DialogBox
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addEditDialogBox.hide();
				addContactButton.setEnabled(true);
			}
		});
	}

	protected List<String> getContactData() {
		List<String> contactData = Arrays.asList(nameTextBox.getText().trim(),
				jobTitleTextBox.getText().trim(), ageListBox
						.getSelectedItemText(), nicknameTextBox.getText()
						.trim(), groupListBox.getSelectedItemText(),
				mangerCheckBox.getValue() ? constants.yes() : constants.no());

		return contactData;
	}

	protected boolean checkIfValidData() {
		// Check name field
		boolean nameValid = !nameTextBox.getText().trim().isEmpty();
		// Check job field
		boolean jobValid = !jobTitleTextBox.getText().trim().isEmpty();

		return nameValid && jobValid;
	}

	protected void addContactToTable(List<String> contactData) {
		// Populate the list
		contacts.add(contactData);
		// Sort the list
		contacts.sortByLastName();
		// Refresh the contacts table
		refreshContactsTable(contacts);
	}

	private void refreshContactsTable(Contacts contactsData) {
		contactsFlexTable.removeAllRows();
		createTableHeader();
		ArrayList<String> contactData;
		for (ContactItem cItem : contacts.getContacts()) {
			contactData = cItem.asStringArray();

			addTableRow(contactData);
		}
	}

	private void addTableRow(ArrayList<String> contactData) {
		int curRow = contactsFlexTable.getRowCount();

		contactsFlexTable.setText(curRow, 0, contactData.get(0));
		contactsFlexTable.setText(curRow + 1, 0, contactData.get(1));
		contactsFlexTable.setText(curRow, 1, contactData.get(2));
		contactsFlexTable.getFlexCellFormatter().setRowSpan(curRow, 1, 2);
		contactsFlexTable.setText(curRow, 2, contactData.get(3));
		contactsFlexTable.getFlexCellFormatter().setRowSpan(curRow, 2, 2);
		contactsFlexTable.setText(curRow, 3, contactData.get(4));
		contactsFlexTable.getFlexCellFormatter().setRowSpan(curRow, 3, 2);
		contactsFlexTable.setText(curRow, 4, contactData.get(5));
		contactsFlexTable.getFlexCellFormatter().setRowSpan(curRow, 4, 2);

		Hyperlink editHyperlink = new Hyperlink();
		editHyperlink.setText(constants.edit());

		Hyperlink deleteHyperlink = new Hyperlink();
		deleteHyperlink.setText(constants.delete());

		contactsFlexTable.setWidget(curRow, 5, editHyperlink);
		contactsFlexTable.getFlexCellFormatter().setRowSpan(curRow, 5, 2);
		contactsFlexTable.setWidget(curRow, 6, deleteHyperlink);
		contactsFlexTable.getFlexCellFormatter().setRowSpan(curRow, 6, 2);

		contactsFlexTable.getRowFormatter().addStyleName(curRow,
				"contactListRow"); //$NON-NLS-1$
		contactsFlexTable.getRowFormatter().addStyleName(curRow + 1,
				"contactListRow"); //$NON-NLS-1$
		contactsFlexTable.getCellFormatter().addStyleName(curRow, 0,
				"contactListCell"); //$NON-NLS-1$
	}

	private void addClickHandler(final FlexTable table) {
		table.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Clicked cell
				Cell cell = table.getCellForEvent(event);

				if (cell != null) {
					int columnClicked = cell.getCellIndex();
					int rowClicked = cell.getRowIndex();
					int listIndex = rowClicked / 2 - 1;
					// If rowClicked==0, then user clicked in the header
					if (rowClicked > 0) {
						// Click in Edit button
						if (columnClicked == 5) {
							ContactItem contactItem = contacts.getContacts()
									.get(listIndex);
							editIndex = listIndex;
							prepareEditDialog(contactItem.asStringArray());
						} else if (columnClicked == 6) {
							String cName = contacts.getContacts()
									.get(listIndex).getName();
							if (Window.confirm(messages.confirmDelete(cName))) {
								contacts.remove(listIndex);
								refreshContactsTable(contacts);
							}
						}
					}
				}
			}
		});
	}

	protected void prepareEditDialog(ArrayList<String> items) {
		nameTextBox.setText(items.get(0));
		jobTitleTextBox.setText(items.get(1));
		ageListBox.setSelectedIndex(Integer.parseInt(items.get(2)) - 15);
		nicknameTextBox.setText(items.get(3));
		groupListBox.setSelectedIndex(groupsList.indexOf(items.get(4)));
		mangerCheckBox.setValue(items.get(5).equals(constants.yes()) ? true
				: false);

		addEditDialogBox.setText(constants.editContact());
		addEditButton.setText(constants.edit());
		addEditDialogBox.center();
	}

	private void assembleEditPanel() {
		// Populate the ageListBox
		for (int i = 15; i <= 100; i++)
			ageListBox.addItem(String.valueOf(i));
		// Populate the groupListBox
		for (String item : groupsList) {
			groupListBox.addItem(item);
		}

		editPanel.add(nameLabel);
		editPanel.add(nameTextBox);
		editPanel.add(jobTitleLabel);
		editPanel.add(jobTitleTextBox);
		editPanel.add(ageLabel);
		editPanel.add(ageListBox);
		editPanel.add(nicknameLabel);
		editPanel.add(nicknameTextBox);
		editPanel.add(groupLabel);
		editPanel.add(groupListBox);
		editPanel.add(mangerCheckBox);
		editPanel.add(addCancelPanel);
		
//		nameTextBox.addStyleName("fullWidth");
//		jobTitleTextBox.addStyleName("fullWidth");
//		nicknameTextBox.addStyleName("fullWidth");
//		editPanel.addStyleName("fullWidth");
	}

	private void assembleMainPanel() {
		mainPanel.add(addPanel);
		mainPanel.add(contactsFlexTable);
		
		mainPanel.addStyleName("mainPanel"); //$NON-NLS-1$
	}

	private void assembleAddCancelPanel() {
		addCancelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		addCancelPanel.add(addEditButton);
		addCancelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		addCancelPanel.add(cancelButton);
		
		addCancelPanel.addStyleName("fullWidth"); //$NON-NLS-1$
	}

	private void assmbleAddContactPanel() {
		addPanel.add(addContactLabel);
		addPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LOCALE_END);
		addPanel.add(addContactButton);
		
		addContactLabel.getElement().setId("addContactLabel"); //$NON-NLS-1$
		addContactButton.getElement().setId("addContactButton"); //$NON-NLS-1$
		addPanel.addStyleName("fullWidth"); //$NON-NLS-1$		
	}

	private void createTableHeader() {
		contactsFlexTable.setText(0, 0, constants.name());
		contactsFlexTable.setText(1, 0, constants.jobTitle());
		contactsFlexTable.setText(0, 1, constants.age());
		contactsFlexTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
		contactsFlexTable.setText(0, 2, constants.nickname());
		contactsFlexTable.getFlexCellFormatter().setRowSpan(0, 2, 2);
		contactsFlexTable.setText(0, 3, constants.group());
		contactsFlexTable.getFlexCellFormatter().setRowSpan(0, 3, 2);
		contactsFlexTable.setText(0, 4, constants.management());
		contactsFlexTable.getFlexCellFormatter().setRowSpan(0, 4, 2);
		contactsFlexTable.setText(0, 5, ""); //$NON-NLS-1$
		contactsFlexTable.getFlexCellFormatter().setRowSpan(0, 5, 2);
		contactsFlexTable.setText(0, 6, ""); //$NON-NLS-1$
		contactsFlexTable.getFlexCellFormatter().setRowSpan(0, 6, 2);

		// Add styles to elements in the stock list table.
		contactsFlexTable.getRowFormatter()
				.addStyleName(0, "contactListHeader"); //$NON-NLS-1$
		contactsFlexTable.getRowFormatter()
				.addStyleName(1, "contactListHeader"); //$NON-NLS-1$
		contactsFlexTable.addStyleName("contactList"); //$NON-NLS-1$
	}
}
