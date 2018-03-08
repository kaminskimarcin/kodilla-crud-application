package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void testGetEmptyListOfTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.maptoTaskDtoList(taskList)).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Test", "Test task"));

        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Test", "Test task"));

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.maptoTaskDtoList(taskList)).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test")))
                .andExpect(jsonPath("$[0].content", is("Test task")));
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        Optional<Task> task = Optional.of(new Task(1L, "Test", "Test task"));
        Long taskId = 1L;
        TaskDto taskDto = new TaskDto(1L, "Test", "Test task");

        when(dbService.getTask(taskId)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task.orElseThrow(TaskNotFoundException::new))).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test task")));
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        TaskDto updatedTask = new TaskDto(1L, "Task - update", "Update test task");
        Task task = new Task(1L, "Task - update", "Update test task");

        when(taskMapper.mapToTask((ArgumentMatchers.any(TaskDto.class)))).thenReturn(task);
        when(dbService.saveTask((ArgumentMatchers.any(Task.class)))).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(updatedTask);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTask);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task - update")))
                .andExpect(jsonPath("$.content", is("Update test task")));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test task");

        doNothing().when(dbService).deleteTask(taskDto.getId());
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteTask(taskDto.getId());
        verifyNoMoreInteractions(dbService);
    }

    @Test
    public void testSaveTask() throws Exception {
        //Given
        Task taskToSave = new Task(1L, "Task - update", "Update test task");

        when(taskMapper.mapToTask((ArgumentMatchers.any(TaskDto.class)))).thenReturn(taskToSave);
        when(dbService.saveTask((ArgumentMatchers.any(Task.class)))).thenReturn(taskToSave);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskToSave);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(dbService, times(1)).saveTask(taskToSave);
        verifyNoMoreInteractions(dbService);
    }
}