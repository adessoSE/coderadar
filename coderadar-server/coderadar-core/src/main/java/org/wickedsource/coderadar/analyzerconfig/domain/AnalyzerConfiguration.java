package org.wickedsource.coderadar.analyzerconfig.domain;

import javax.persistence.*;
import org.wickedsource.coderadar.project.domain.Project;

/** An AnalyzerConfiguration stores the configuration for a single analyzer plugin in a project. */
@Entity
@Table(name = "analyzer_configuration")
@SequenceGenerator(
	name = "analyzer_configuration_sequence",
	sequenceName = "seq_acon_id",
	allocationSize = 1
)
public class AnalyzerConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analyzer_configuration_sequence")
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "analyzer_name")
	private String analyzerName;

	@Column(name = "enabled")
	private Boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public void setAnalyzerName(String analyzerName) {
		this.analyzerName = analyzerName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
