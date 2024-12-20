package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.LibraryMemberSeedDto;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private static final String FILE_PATH = "src/main/resources/files/json/library-members.json";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, LibraryMemberRepository libraryMemberRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.libraryMemberRepository = libraryMemberRepository;
    }


    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder sb = new StringBuilder();

        LibraryMemberSeedDto[] libraryMemberSeedDtos = this.gson.fromJson(readLibraryMembersFileContent(), LibraryMemberSeedDto[].class);

        for (LibraryMemberSeedDto libraryMemberSeedDto : libraryMemberSeedDtos) {

            Optional<LibraryMember> optional = this.libraryMemberRepository.findByPhoneNumber(libraryMemberSeedDto.getPhoneNumber());


            if(!this.validationUtil.isValid(libraryMemberSeedDto) || optional.isPresent()) {
                sb.append("Invalid library member").append(System.lineSeparator());
                continue;

            }

            LibraryMember libraryMember = this.modelMapper.map(libraryMemberSeedDto, LibraryMember.class);
            libraryMemberRepository.saveAndFlush(libraryMember);

            sb.append(String.format("Successfully imported library member %s - %s", libraryMember.getFirstName(), libraryMember.getLastName())).append(System.lineSeparator());


        }


        return sb.toString();
    }
}
