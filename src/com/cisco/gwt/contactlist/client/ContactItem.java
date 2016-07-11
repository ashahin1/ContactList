package com.cisco.gwt.contactlist.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;

public class ContactItem implements IGroups {
	private String name;
	private String jobTitle;
	private int age;
	private String nickname;
	private int group;
	private boolean manager;

	private ContactListConstants constants = GWT
			.create(ContactListConstants.class);

	public ContactItem(String name, String jobTitle, int age, String nickname,
			int group, boolean manager) {

		this.name = name;
		this.jobTitle = jobTitle;
		this.age = age;
		this.nickname = nickname;
		this.group = group;
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public ArrayList<String> asStringArray() {
		ArrayList<String> items = new ArrayList<>(6);

		items.add(name);
		items.add(jobTitle);
		items.add(String.valueOf(age));
		items.add(nickname);
		items.add(groupsList.get(group));
		items.add(manager ? constants.yes() : constants.no());

		return items;
	}

}
