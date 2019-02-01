package org.wickedsource.coderadar.user.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.core.rest.AbstractResourceAssembler;
import org.wickedsource.coderadar.user.domain.User;
import org.wickedsource.coderadar.user.domain.UserResource;

@Component
public class UserResourceAssembler extends AbstractResourceAssembler<User, UserResource> {

	public UserResourceAssembler() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(User entity) {
		UserResource userResource = new UserResource(entity.getUsername());
		userResource.add(
				linkTo(methodOn(UserController.class).getUser(entity.getId())).withRel("self"));
		return userResource;
	}
}
