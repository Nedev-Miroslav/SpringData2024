package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.TaskRootDto;
import softuni.exam.models.dto.xmls.TaskSeedDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.parsers.MyXmlParser;
import softuni.exam.util.validation.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";

    private final TasksRepository tasksRepository;

    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final CarsRepository carsRepository;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final MyXmlParser xmlParser;

    public TasksServiceImpl(TasksRepository tasksRepository, MechanicsRepository mechanicsRepository, PartsRepository partsRepository, CarsRepository carsRepository, ValidationUtil validationUtil, ModelMapper modelMapper, MyXmlParser xmlParser) {
        this.tasksRepository = tasksRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.carsRepository = carsRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(TASKS_FILE_PATH)));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        TaskRootDto taskRootDto = xmlParser.parse(TaskRootDto.class, TASKS_FILE_PATH);


        for (TaskSeedDto taskSeedDto : taskRootDto.getTaskSeedDto()) {

            Optional<Mechanic> optionalMechanic = this.mechanicsRepository.findByFirstName(taskSeedDto.getMechanic().getFirstName());

            if (!this.validationUtil.isValid(taskSeedDto) || optionalMechanic.isEmpty()) {

                sb.append("Invalid task").append(System.lineSeparator());
                continue;
            }

            Task task = this.modelMapper.map(taskSeedDto, Task.class);
            task.setMechanic(optionalMechanic.get());
            task.setPart(this.partsRepository.getById(taskSeedDto.getPart().getId()));
            task.setCar(this.carsRepository.getById(taskSeedDto.getPart().getId()));

            this.tasksRepository.saveAndFlush(task);

            sb.append(String.format("Successfully imported task %.2f", task.getPrice())).append(System.lineSeparator());
        }


        return sb.toString();
    }






    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return this.tasksRepository.findAllCoupeTasks()
                .stream()
                .map(t -> String.format("Car %s %s with %dkm\n" +
                        "-Mechanic: %s %s - task №%d:\n" +
                        " --Engine: %.1f\n" +
                        "---Price: %.2f$\n"
                        , t.getCar().getCarMake()
                        , t.getCar().getCarModel()
                        , t.getCar().getKilometers()
                        , t.getMechanic().getFirstName()
                        , t.getMechanic().getLastName()
                        , t.getId()
                        , t.getCar().getEngine()
                        , t.getPrice()
                        )).collect(Collectors.joining());









    }
}
