package taskService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskService.domain.Constants;
import taskService.domain.Subtask;
import taskService.repository.SubtaskRepository;

@Service
public class SubtaskServiceImpl implements SubtaskService {
    @Autowired
    private SubtaskRepository subtaskRepository;

    @Override
    public int generateSubtaskId() {
        int newId = Constants.RESERVED_NUM_OF_IDS + (int)subtaskRepository.count();
        return newId;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtaskRepository.save(subtask);
    }

    @Override
    public void deleteAllSubtasks() {
        subtaskRepository.deleteAll();
    }
}
