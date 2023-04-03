package google.drive.domain;

import google.drive.infra.AbstractEvent;
import java.util.*;
import lombok.Data;

@Data
public class VideoFileStreamed extends AbstractEvent {

    private Long id;
    private Long fileId;
    private String url;
}
