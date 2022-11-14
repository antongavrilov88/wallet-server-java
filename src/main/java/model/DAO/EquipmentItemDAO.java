package model.DAO;

import exceptions.DAOException;
import model.EquipmentItem;
import model.User;
import model.repository.EquipmentItemRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EquipmentItemDAO {
    private EquipmentItemRepository repository;

    public EquipmentItemDAO(EquipmentItemRepository repository) {
        this.repository = repository;
    }

    public List<EquipmentItem> findByName(String name) {
        ArrayList<EquipmentItem> result = new ArrayList<>();
        for (EquipmentItem item : repository.findAll()) {
            if (item.getName().equals(name)) {
                result.add(item);
            }
        }
        return result;
    }


    public EquipmentItem findById(int id) {
        return repository.findById(id).get();
    }


    public EquipmentItem save(EquipmentItem item) {
        try {
            return repository.save(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public void delete(EquipmentItem item) {
        repository.delete(item);
    }


    public void update(EquipmentItem item) throws DAOException {
        if (item.getId() != 0) {
            repository.save(item);
        } else {
            throw new DAOException();
        }
    }

    public List<EquipmentItem> findAll() {
        return repository.findAll();
    }
}
