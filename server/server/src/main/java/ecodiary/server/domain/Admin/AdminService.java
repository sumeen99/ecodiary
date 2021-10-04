package ecodiary.server.domain.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;


    @Transactional(readOnly = true)
    public Long checkAdmin(Long adminId){
        Admin admin=adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("해당 관리자가 없습니다. id=" + adminId));
        return admin.getId();
    }
}
