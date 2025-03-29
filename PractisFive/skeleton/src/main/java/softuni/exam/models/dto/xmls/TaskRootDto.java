package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskRootDto implements Serializable {

    @XmlElement(name = "task")
    private List<TaskSeedDto> taskSeedDto;


    public List<TaskSeedDto> getTaskSeedDto() {
        return taskSeedDto;
    }

    public void setTaskSeedDto(List<TaskSeedDto> taskSeedDto) {
        this.taskSeedDto = taskSeedDto;
    }
}
