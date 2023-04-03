package google.drive.domain;

import google.drive.domain.*;
import google.drive.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class VideoFileStreamed extends AbstractEvent {

    private Long id;
    private Long fileId;
    private String url;

    public VideoFileStreamed(Video aggregate) {
        super(aggregate);
    }

    public VideoFileStreamed() {
        super();
    }
}
