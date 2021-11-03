package ecodiary.server.domain.Admin;

import ecodiary.server.global.OutputConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;


    @Transactional(readOnly = true)
    public Long checkAdmin(Long adminId){
        Admin admin=adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException(OutputConst.NO_ADMIN + adminId));
        return admin.getId();
    }
}
