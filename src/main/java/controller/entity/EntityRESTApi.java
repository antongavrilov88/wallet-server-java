package controller.entity;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.dto.RequestDto;
import service.dto.ResponseDto;

import java.util.List;

@Component
public interface EntityRESTApi<T> {

    ResponseEntity findById(int id);
    List<T> findAll();
    ResponseDto<T> create(RequestDto<T> entity);
    void delete(RequestDto<T> entity);

}
