package service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Links links;
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private Map<String, Token> rel = new HashMap<>();

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private int id;

        public UserDto() {
        }

        public void transform(EntityModel<User> user, Token accessToken, Token refreshToken) {
            setUser(user);
            setEmail(user.getContent().getEmail());
            setIsAdmin(user.getContent().getIsAdmin());
            setLinks(user.getLinks());
            setId(user.getContent().getId());
            rel.put("access_token", accessToken);
            rel.put("refresh_token", refreshToken);
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public Map<String, Token> getRel() {
            return rel;
        }

        public void setRel(Map<String, Token> rel) {
            this.rel = rel;
        }
    }
}
