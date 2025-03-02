package com.bank.tech.accounts.imp;

import com.bank.tech.accounts.dto.BranchDTO;
import com.bank.tech.accounts.entity.Branch;
import com.bank.tech.accounts.repository.BranchRepository;
import com.bank.tech.accounts.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImp implements BranchService {

    @Autowired
    BranchRepository repository;
    @Override
    public void creatBranch(BranchDTO branchDTO) {
        Branch branch= Branch.builder()
                .name(branchDTO.getName())
                .build();
        repository.save(branch);
    }
}
