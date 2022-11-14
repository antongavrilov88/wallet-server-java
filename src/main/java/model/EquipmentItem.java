package model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Schema(description = "Equipment item entity")
@Component
@Entity
@Table(name = "equipment_items")
public class EquipmentItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "serial_number")
    private long serialNumber;
    @Column(name = "palce_of_usage")
    private String placeOfUsage;
    @Column(name = "storage_status")
    private String storageStatus;
    @Column(name = "next_service")
    private Date nextService;
    @Column(name = "last_service")
    private Date lastService;
    @Column(name = "operator")
    private String operator;
    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_warranty_on")
    private boolean isWarrantyOn;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPlaceOfUsage() {
        return placeOfUsage;
    }

    public void setPlaceOfUsage(String placeOfUsage) {
        this.placeOfUsage = placeOfUsage;
    }

    public String getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(String storageStatus) {
        this.storageStatus = storageStatus;
    }

    public Date getNextService() {
        return nextService;
    }

    public void setNextService(Date nextService) {
        this.nextService = nextService;
    }

    public Date getLastService() {
        return lastService;
    }

    public void setLastService(Date lastService) {
        this.lastService = lastService;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWarrantyOn() {
        return isWarrantyOn;
    }

    public void setWarrantyOn(boolean warrantyOn) {
        isWarrantyOn = warrantyOn;
    }
}
