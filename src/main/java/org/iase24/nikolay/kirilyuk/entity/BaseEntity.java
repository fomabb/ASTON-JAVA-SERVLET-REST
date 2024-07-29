package org.iase24.nikolay.kirilyuk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_seq")
    @SequenceGenerator(name = "base_entity_seq", sequenceName = "base_entity_sequence", allocationSize = 1)
    private Long id;
}
