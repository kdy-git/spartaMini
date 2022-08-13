package com.sparta.miniproject.scheduler;

import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.sparta.miniproject.S3.S3Service;
import com.sparta.miniproject.mapping.ImgUrlMapping;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    @Scheduled(cron = "10 * * * * *")
    public void deleteOrphanImage() {
        List<String> dbImgList = postRepository.findAllBy(ImgUrlMapping.class)
                .stream()
                .filter(url -> url != null)
                .map(url ->
                        url.getImgUrl()
                                .split("/")[url.getImgUrl().split("/").length - 1])
                .collect(Collectors.toList());

        List<DeleteObjectsRequest.KeyVersion> keys = s3Service.getImageKeys();

        keys.stream().forEach(key -> {
            if(dbImgList.contains(key.getKey())) {
                keys.remove(key);
            }
        });

        log.info("s3 버킷 고아 사진 객체 삭제");
        s3Service.deleteImages(keys);
        log.info("s3 버킷 고아 사진 객체 스케줄러 종료");
    }
}
