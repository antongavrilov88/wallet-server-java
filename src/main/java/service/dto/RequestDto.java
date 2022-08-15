package service.dto;

import model.User;
import org.springframework.stereotype.Component;

@Component
public class RequestDto {
    String type;
    User data;

    public RequestDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
