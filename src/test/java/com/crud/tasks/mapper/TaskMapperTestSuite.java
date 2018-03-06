package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test test");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        Long id = task.getId();
        String title = task.getTitle();
        String content = task.getContent();
        //Then
        assertEquals("Test", title);
        assertEquals("Test test", content);
        assertEquals(new Long(1), id);
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "Test", "Test test");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        Long id = taskDto.getId();
        String title = taskDto.getTitle();
        String content = taskDto.getContent();
        //Then
        assertEquals(new Long(1), id);
        assertEquals("Test", title);
        assertEquals("Test test", content);
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        Task task = new Task(1L, "Test", "Test test");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        //When
        List<TaskDto> taskDtoList = taskMapper.maptoTaskDtoList(taskList);
        int size = taskDtoList.size();
        String title = taskDtoList.get(0).getTitle();
        String content = taskDtoList.get(0).getContent();
        //Then
        assertEquals(1, size);
        assertEquals("Test", title);
        assertEquals("Test test", content);
    }

}
