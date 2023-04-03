package google.drive.infra;

import google.drive.config.kafka.KafkaProcessor;
import google.drive.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DashboardViewHandler {

    @Autowired
    private DashboardRepository dashboardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFileUploaded_then_CREATE_1(
        @Payload FileUploaded fileUploaded
    ) {
        try {
            if (!fileUploaded.validate()) return;

            // view 객체 생성
            Dashboard dashboard = new Dashboard();
            // view 객체에 이벤트의 Value 를 set 함
            dashboard.setId(fileUploaded.getId());
            dashboard.setPath(fileUploaded.getPath());
            dashboard.setSize(fileUploaded.getSize());
            dashboard.setName(fileUploaded.getName());
            dashboard.setIsUploaded(true);
            // view 레파지 토리에 save
            dashboardRepository.save(dashboard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFileIndexed_then_UPDATE_1(
        @Payload FileIndexed fileIndexed
    ) {
        try {
            if (!fileIndexed.validate()) return;
            // view 객체 조회
            Optional<Dashboard> dashboardOptional = dashboardRepository.findById(
                fileIndexed.getFileId()
            );

            if (dashboardOptional.isPresent()) {
                Dashboard dashboard = dashboardOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                dashboard.setIsIndexed(true);
                // view 레파지 토리에 save
                dashboardRepository.save(dashboard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenVideoFileStreamed_then_UPDATE_2(
        @Payload VideoFileStreamed videoFileStreamed
    ) {
        try {
            if (!videoFileStreamed.validate()) return;
            // view 객체 조회
            Optional<Dashboard> dashboardOptional = dashboardRepository.findById(
                videoFileStreamed.getFileId()
            );

            if (dashboardOptional.isPresent()) {
                Dashboard dashboard = dashboardOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                dashboard.setVideoUrl(videoFileStreamed.getUrl());
                // view 레파지 토리에 save
                dashboardRepository.save(dashboard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
