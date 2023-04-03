package google.drive.domain;

import google.drive.VideoProcessingApplication;
import google.drive.domain.VideoFileStreamed;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Video_table")
@Data
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long fileId;

    private String url;

    @PostPersist
    public void onPostPersist() {
        VideoFileStreamed videoFileStreamed = new VideoFileStreamed(this);
        videoFileStreamed.publishAfterCommit();
    }

    public static VideoRepository repository() {
        VideoRepository videoRepository = VideoProcessingApplication.applicationContext.getBean(
            VideoRepository.class
        );
        return videoRepository;
    }

    public static void streamVideoFile(FileUploaded fileUploaded) {
        /** Example 1:  new item 
        Video video = new Video();
        repository().save(video);

        VideoFileStreamed videoFileStreamed = new VideoFileStreamed(video);
        videoFileStreamed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(fileUploaded.get???()).ifPresent(video->{
            
            video // do something
            repository().save(video);

            VideoFileStreamed videoFileStreamed = new VideoFileStreamed(video);
            videoFileStreamed.publishAfterCommit();

         });
        */

    }
}
