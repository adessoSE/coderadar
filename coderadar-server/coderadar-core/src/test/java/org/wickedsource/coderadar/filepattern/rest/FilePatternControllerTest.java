package org.wickedsource.coderadar.filepattern.rest;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.EMPTY;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.FilePatterns.SINGLE_PROJECT_WITH_FILEPATTERNS;
import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;
import static org.wickedsource.coderadar.factories.resources.ResourceFactory.filePatternResource;
import static org.wickedsource.coderadar.testframework.template.JsonHelper.toJsonWithoutLinks;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.containsResource;
import static org.wickedsource.coderadar.testframework.template.ResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.http.MediaType;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

@Category(ControllerTest.class)
public class FilePatternControllerTest extends ControllerTestTemplate {

	@Test
	@DatabaseSetup(SINGLE_PROJECT)
	@ExpectedDatabase(SINGLE_PROJECT_WITH_FILEPATTERNS)
	public void setFilePatterns() throws Exception {
		FilePatternResource filepatterns = filePatternResource().filePatterns();
		ConstrainedFields fields = fields(FilePatternResource.class);
		mvc()
				.perform(
						post("/projects/1/files")
								.content(toJsonWithoutLinks(filepatterns))
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(containsResource(FilePatternResource.class))
				.andDo(
						document(
								"filepatterns/create-update",
								links(
										halLinks(),
										linkWithRel("self")
												.description("Link to the list of file patterns of this project."),
										linkWithRel("project")
												.description("Link to the project these file patterns belong to.")),
								requestFields(
										fields
												.withPath("filePatterns[].pattern")
												.description(
														"Ant-style file pattern matching a set of files in the project's code base."),
										fields
												.withPath("filePatterns[].inclusionType")
												.description(
														"Either 'INCLUDE' if the matching set of files is to be included or 'EXCLUDE' if it is to be excluded."),
										fields
												.withPath("filePatterns[].fileSetType")
												.description(
														"The set of files the files matching this pattern belong to. Currently, the only available value is 'SOURCE' describing source code files."))));
	}

	@Test
	@DatabaseSetup(EMPTY)
	@ExpectedDatabase(EMPTY)
	public void setFilePatternsWithInvalidProject() throws Exception {
		FilePatternResource filepatterns = filePatternResource().filePatterns();
		mvc()
				.perform(
						post("/projects/1/files")
								.content(toJsonWithoutLinks(filepatterns))
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DatabaseSetup(SINGLE_PROJECT_WITH_FILEPATTERNS)
	@ExpectedDatabase(SINGLE_PROJECT_WITH_FILEPATTERNS)
	public void getFilePatterns() throws Exception {
		mvc()
				.perform(get("/projects/1/files"))
				.andExpect(status().isOk())
				.andExpect(containsResource(FilePatternResource.class))
				.andDo(document("filepatterns/get"));
	}

	@Test
	@DatabaseSetup(EMPTY)
	@ExpectedDatabase(EMPTY)
	public void getFilePatternsWithInvalidProject() throws Exception {
		mvc()
				.perform(get("/projects/1/files").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
