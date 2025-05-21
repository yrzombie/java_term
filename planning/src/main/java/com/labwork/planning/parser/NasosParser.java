package com.labwork.planning.parser;

import com.labwork.planning.model.Project;
import com.labwork.planning.model.Task;
import com.labwork.planning.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NasosParser {

    private final ProjectRepository projectRepository;

    public void importFromNasosFile(int variantCode, String rootParentCode) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("nasos.txt"),
                        StandardCharsets.UTF_8))) {

            String line;
            Project project = null;
            List<Task> tasks = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\t");

                if (parts.length < 11) continue;

                String name = parts[0];
                String code1 = parts[1];
                String code2 = parts[2];
                String code3 = parts[3];
                String parentCode = parts[4];
                String durationStr = parts[5];
                String operationTimeStr = parts[6];
                String setupTimeStr = parts[7];
                String batchSizeStr = parts[8];
                String type = parts[9];
                String workshop = parts[10];

                // Создаём проект, если код совпадает с variantCode
                if (project == null &&
                        (code1.equals(String.valueOf(variantCode)) ||
                         code2.equals(String.valueOf(variantCode)) ||
                         code3.equals(String.valueOf(variantCode)))) {
                    project = new Project();
                    project.setName(name);
                    System.out.println("✅ Проект создан: " + name);
                }

                // Добавляем задачи с parentCode == rootParentCode и type == "т"
                if (project != null &&
                        parentCode.equals(rootParentCode) &&
                        "т".equalsIgnoreCase(type)) {

                    Task task = new Task();
                    task.setName(name);
                    task.setCode(code1);
                    task.setParentCode(parentCode);
                    task.setType(type);
                    task.setWorkshop(workshop);

                    try { task.setDuration(Integer.parseInt(durationStr)); } catch (Exception ignored) {}
                    try { task.setOperationTime(Double.parseDouble(operationTimeStr)); } catch (Exception ignored) {}
                    try { task.setSetupTime(Double.parseDouble(setupTimeStr)); } catch (Exception ignored) {}
                    try { task.setBatchSize(Integer.parseInt(batchSizeStr)); } catch (Exception ignored) {}

                    task.setProject(project);
                    tasks.add(task);
                    System.out.println("✅ Задача добавлена: " + name);
                }
            }

            if (project != null) {
                project.setTasks(tasks);
                projectRepository.save(project);
                System.out.printf("✅ Проект '%s' сохранён. Задач: %d%n", project.getName(), tasks.size());
            } else {
                throw new RuntimeException("❌ Проект не найден по коду варианта.");
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Ошибка при импорте nasos.txt: " + e.getMessage(), e);
        }
    }
}
