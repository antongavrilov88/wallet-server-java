package service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import model.User;
import org.springframework.stereotype.Component;

@Component
public class RequestDto<T> {
    String type;
    T data;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String issuer;

    public RequestDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
