package ecodiary.server.domain.EduPosts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EduPostsService {
    private final EduPostsRepository eduPostsRepository;

}
