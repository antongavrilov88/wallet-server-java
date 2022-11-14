package service.dto;

import org.json.JSONObject;
import org.springframework.hateoas.EntityModel;

public class EntityDto<T> extends EntityModel<T> {
    T entity;
    JSONObject rels;
    JSONObject link;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public JSONObject getRels() {
        return rels;
    }

    public void setRels(JSONObject rels) {
        this.rels = rels;
    }

    public JSONObject getLink() {
        return link;
    }

    public void setLink(JSONObject link) {
        this.link = link;
    }
}
