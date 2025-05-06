package org.example.bcpv2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.bcpv2.games.chess.eunums.Color;

import java.util.List;

@Data
@Getter
@Setter
public class ColorDto {
    List<Color> color;
}
