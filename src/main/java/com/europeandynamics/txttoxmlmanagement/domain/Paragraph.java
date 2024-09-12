package com.europeandynamics.txttoxmlmanagement.domain;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "paragraph")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paragraph {
    private List<String> line;
}
