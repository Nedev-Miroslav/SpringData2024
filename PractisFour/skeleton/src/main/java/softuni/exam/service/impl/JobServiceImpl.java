package softuni.exam.service.impl;

import jdk.dynalink.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.JobRootDto;
import softuni.exam.models.dto.xmls.JobSeedDto;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.parsers.XmlParser;
import softuni.exam.util.validation.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private static final String FILE_PATH = "src/main/resources/files/xml/jobs.xml";

    private final JobRepository jobRepository;

    private final CompanyRepository companyRepository;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));

    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JobRootDto jobRootDto = xmlParser.parse(JobRootDto.class, FILE_PATH);



        for (JobSeedDto jobSeedDto : jobRootDto.getJobSeedDto()) {



            if (!this.validationUtil.isValid(jobSeedDto)){
                sb.append("Invalid job").append(System.lineSeparator());
                continue;

            }

            Job job = this.modelMapper.map(jobSeedDto, Job.class);
            job.setTitle(jobSeedDto.getJobTitle());
            job.setHoursAWeek(jobSeedDto.getHoursAWeek());
            job.setDescription(jobSeedDto.getDescription());
            job.setSalary(jobSeedDto.getSalary());
            job.setCompany(this.companyRepository.getById(jobSeedDto.getCompanyId()));

            this.jobRepository.saveAndFlush(job);

            sb.append(String.format("Successfully imported job %s", job.getTitle())).append(System.lineSeparator());


        }


        return sb.toString();
    }

    @Override
    public String getBestJobs() {
        return this.jobRepository.findBestJobs()
                .stream()
                .map(j -> String.format("Job title %s%n" +
                        "-Salary: %.2f$%n" +
                        "--Hours a week: %.2fh.%n%n"
                        ,j.getTitle()
                        ,j.getSalary()
                        ,j.getHoursAWeek()))
                .collect(Collectors.joining());
    }
}
