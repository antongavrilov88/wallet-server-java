package service.dto;

import controller.UserController;
import model.Token;
import model.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserEntitylAssembler {


    public EntityDto<User> toModel(User entity, Token accessToken, Token refreshToken) {
        EntityDto<User> entityDto = new EntityDto<>();
        JSONObject links = new JSONObject();
        String self = linkTo(methodOn(UserController.class).one(entity.getId())).withSelfRel().toString();
        String resource = linkTo(methodOn(UserController.class).all()).withRel("users").toString();
        links.put("self", self);
        links.put("resource", resource);
        entityDto.setEntity(entity);
        entityDto.setLink(links);
        JSONObject rels = new JSONObject();
        rels.put("access_token", accessToken);
        rels.put("refresh_token", refreshToken);
        entityDto.setRels(rels);
        return entityDto;
    }

    //TODO implement this method
    /* @Override
    public CollectionModel<EntityModel<User>> toCollectionModel(Iterable<? extends User> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    } */
}
