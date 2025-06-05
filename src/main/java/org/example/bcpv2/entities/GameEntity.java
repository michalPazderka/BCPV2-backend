package org.example.bcpv2.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * This class was previously a JPA entity but has been converted to a simple POJO
 * since the application no longer uses a database.
 */
@Getter
@Setter
public class GameEntity {

    Long id;

}
