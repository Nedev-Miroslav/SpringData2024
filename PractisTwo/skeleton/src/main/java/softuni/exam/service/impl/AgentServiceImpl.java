package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.AgentSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.Validations.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    private static final String FILE_PATH = "src/main/resources/files/json/agents.json";

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;



    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        AgentSeedDto[] agentSeedDtos = this.gson.fromJson(readAgentsFromFile(), AgentSeedDto[].class);

        for (AgentSeedDto agentSeedDto : agentSeedDtos) {

            Optional<Agent> optionalName = this.agentRepository.findByFirstName(agentSeedDto.getFirstName());
            Optional<Agent> optionalEmail = this.agentRepository.findByEmail(agentSeedDto.getEmail());


            if(!this.validationUtil.isValid(agentSeedDto) || optionalName.isPresent() || optionalEmail.isPresent()){
                sb.append("Invalid agent").append(System.lineSeparator());
                continue;

            }

            Agent agent = this.modelMapper.map(agentSeedDto, Agent.class);
            agent.setTown(this.townRepository.findByTownName(agentSeedDto.getTown()).get());
            this.agentRepository.saveAndFlush(agent);

            sb.append(String.format("Successfully imported agent - %s %s", agent.getFirstName(), agent.getLastName())).append(System.lineSeparator());

        }

        return sb.toString();

    }
}
