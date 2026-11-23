package fr.stefangeorgesco.smartbar.backoffice.tables;

import fr.stefangeorgesco.smartbar.backoffice.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@jakarta.persistence.Table(name = "Sbo_Table", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Table extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    @Positive
    private Integer seatCount;

    @NotNull
    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
