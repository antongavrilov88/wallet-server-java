package controller.entity;

import model.EquipmentItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import service.EntityServiceInterface;
import service.dto.RequestDto;
import service.dto.ResponseDto;

import java.util.List;

@Component
public class EquipmentItemRESTApi implements EntityRESTApi<EquipmentItem> {

    EntityServiceInterface<EquipmentItem> equipmentItemEntityService;
    @Override
    public ResponseEntity findById(int id) {
        //TODO wrap it in responseentity
        return null;
        // return equipmentItemEntityService.findById(id);
    }

    @Override
    public List<EquipmentItem> findAll() {
        //TODO wrap it in responseentity
        return equipmentItemEntityService.findAll();
    }

    @Override
    public ResponseDto create(RequestDto entity) {
        //TODO unwrap requestDto to User
        return null;
        //return equipmentItemEntityService.create(entity);
    }

    @Override
    public void delete(RequestDto entity) {
        //TODO unwrap requestDto to User

        //equipmentItemEntityService.delete(entity);
    }
}
