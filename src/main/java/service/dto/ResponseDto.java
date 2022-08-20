package service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import model.Token;
import model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Component;

//TODO delete commented code

@Component
@Schema
public class ResponseDto {
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

    public ResponseDto(String type, UserDto data) {
        this.type = type;
        this.data = data;
    }

    public ResponseDto() {
    }

    @Schema
    public class UserDto {
        @JsonIgnore
        private EntityModel<User> user;
        private String email;
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private boolean isAdmin;
        //@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        // private String pass;

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Links links;
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Token rel;

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private int id;

        public UserDto() {
        }

        public void transform(EntityModel<User> user, Token rel) {
            //TODO refactor this method, use setters
            this.user = user;
            this.rel = rel;
            this.email = user.getContent().getUsername();
            this.isAdmin = user.getContent().getIsAdmin();
            // this.pass = user.getContent().getPassword();
            this.links = user.getLinks();
            this.id = user.getContent().getId();
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        /* public String getPassword() {
            return pass;
        } */

        /* public void setPassword(String pass) {
            this.pass = pass;
        } */

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
