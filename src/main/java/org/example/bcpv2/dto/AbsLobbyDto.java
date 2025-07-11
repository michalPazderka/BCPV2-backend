package org.example.bcpv2.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AbsLobbyDto {
    String id;
    Long numberOfFreePlaces;
    String gameName;
}
