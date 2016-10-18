package de.espirit.firstspirit.webedit.test.ui.firstspirit.component;

import de.espirit.firstspirit.access.project.Project;

public class FSProjectImpl implements FSProject {
	private final Project project;

	public FSProjectImpl(Project projectByName) {
		this.project = projectByName;
	}

	@Override
	public Project get() {
		return project;
	}
}
