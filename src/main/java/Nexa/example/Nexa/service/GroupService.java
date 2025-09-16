package Nexa.example.Nexa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.repository.GroupRepository;

@Service
public class GroupService {

    @Autowired
    
    private GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public List<Group> getGroupsByTeacher(Long teacherId) {
        return groupRepository.findByTeacherId(teacherId);
    }
}