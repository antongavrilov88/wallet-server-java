package service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.media.Schema;
import model.Token;
import model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Schema
public class RequestResponseDto {
    private String type;
    private UserDto data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDto getData() {
        return data;
    }

    public void setData(UserDto data) {
        this.data = data;
    }

    public RequestResponseDto(String type, UserDto data) {
        this.type = type;
        this.data = data;
    }

    public RequestResponseDto() {
    }

    @Schema
    public class UserDto {
        @JsonIgnore
        private EntityModel<User> user;
        private String email;
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private boolean isAdmin;
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        private String pass;

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Links links;
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Token rel;

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private int id;

        public UserDto(EntityModel<User> user, Token rel) {
            this.user = user;
            this.rel = rel;
            this.email = user.getContent().getEmail();
            this.isAdmin = user.getContent().getIsAdmin();
            this.pass = user.getContent().getPass();
            this.links = user.getLinks();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean admin) {
            isAdmin = admin;
        }

        public EntityModel<User> getUser() {
            return user;
        }

        public void setUser(EntityModel<User> user) {
            this.user = user;
        }

        public Token getRel() {
            return rel;
        }

        public void setRel(Token rel) {
            this.rel = rel;
        }
    }
}
