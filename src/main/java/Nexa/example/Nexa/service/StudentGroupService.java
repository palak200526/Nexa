package Nexa.example.Nexa.service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentGroupService {

    private final GroupRepository groupRepo;

    public StudentGroupService(GroupRepository groupRepo) {
        this.groupRepo = groupRepo;
    }

    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Group ID"));
    }

    public Group saveGroup(Group group) {
        return groupRepo.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepo.deleteById(id);
    }
}
