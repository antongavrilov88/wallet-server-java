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
public class ResponseDto<T> {
    private String type;
    private EntityDto<T> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EntityDto<T> getData() {
        return data;
    }

    public void setData(EntityDto<T> data) {
        this.data = data;
    }

    public ResponseDto(String type, EntityDto<T> data) {
        this.type = type;
        this.data = data;
    }

    public ResponseDto() {
    }
}
