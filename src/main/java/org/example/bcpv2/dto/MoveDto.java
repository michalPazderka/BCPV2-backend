package org.example.bcpv2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MoveDto {
    String gameId;
    String move;
}
