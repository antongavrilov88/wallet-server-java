package model.repository;

import model.EquipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentItemRepository extends JpaRepository<EquipmentItem, Integer> {
}
