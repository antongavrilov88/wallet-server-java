package service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import model.Token;
import model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Component;


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
        private MyLinks links;
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        private MyRels rel;

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
            rel = new MyRels(accessToken, refreshToken);
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MyLinks getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            String[] array = new String[2];
            int i = 0;
            for (Link link : links) {
                array[i] = link.toUri().toString();
                i++;
            }
            this.links = new MyLinks(array[0], array[1]);
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

        public MyRels getRel() {
            return rel;
        }

        public void setRel(MyRels rel) {
            this.rel = rel;
        }
    }

    public class MyLinks {
        String self;
        String resource;

        public MyLinks(String self, String recourse) {
            this.self = self;
            this.resource = recourse;
        }

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }
    }

    public class MyRels {
        Token access_token;
        Token refresh_token;

        public MyRels(Token accessToken, Token refreshToken) {
            this.access_token = accessToken;
            this.refresh_token = refreshToken;
        }

        public Token getAccess_token() {
            return access_token;
        }

        public void setAccess_token(Token access_token) {
            this.access_token = access_token;
        }

        public Token getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(Token refresh_token) {
            this.refresh_token = refresh_token;
        }
    }
}
