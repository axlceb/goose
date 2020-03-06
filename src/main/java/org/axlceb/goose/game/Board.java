package org.axlceb.goose.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @JsonProperty("step-count")
    private Integer stepCount;
    private Map<Integer, String> steps;
}
