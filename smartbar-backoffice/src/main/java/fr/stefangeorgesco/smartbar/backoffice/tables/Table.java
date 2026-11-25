package fr.stefangeorgesco.smartbar.backoffice.tables;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@jakarta.persistence.Table(name = "Sbo_Table", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Table extends PanacheEntity {

    @NotNull
    public String name;

    @NotNull
    @Positive
    public Integer seatCount;

    @NotNull
    public Boolean active;
}
