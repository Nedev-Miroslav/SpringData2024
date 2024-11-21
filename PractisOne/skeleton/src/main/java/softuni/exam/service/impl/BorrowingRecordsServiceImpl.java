package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.BorrowingRecordRootDto;
import softuni.exam.models.dto.xmls.BorrowingRecordSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";


    private final BorrowingRecordRepository borrowingRecordRepository;

    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(BorrowingRecordRootDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        BorrowingRecordRootDto borrowingRecordRootDto = (BorrowingRecordRootDto) unmarshaller.unmarshal(new File(FILE_PATH));

        for (BorrowingRecordSeedDto borrowingRecordSeedDto : borrowingRecordRootDto.getBorrowingRecordSeedDto()) {

            Optional<Book> optionalBook = this.bookRepository.findByTitle(borrowingRecordSeedDto.getBooks().getTitle());
            Optional<LibraryMember> optionalLibraryMember = this.libraryMemberRepository.findById(borrowingRecordSeedDto.getMember().getId());


            if (!this.validationUtil.isValid(borrowingRecordSeedDto) || optionalBook.isEmpty() || optionalLibraryMember.isEmpty()) {
                sb.append("Invalid borrowing record").append(System.lineSeparator());
                continue;

            }

            BorrowingRecord borrowingRecord = this.modelMapper.map(borrowingRecordSeedDto, BorrowingRecord.class);
            borrowingRecord.setBook(optionalBook.get());
            borrowingRecord.setLibraryMember(optionalLibraryMember.get());

            this.borrowingRecordRepository.saveAndFlush(borrowingRecord);

            sb.append(String.format("Successfully imported borrowing record %s - %s", borrowingRecord.getBook().getTitle(), borrowingRecord.getBorrowDate())).append(System.lineSeparator());

        }

        return sb.toString();
    }

    @Override
    public String exportBorrowingRecords() {
        return this.borrowingRecordRepository.findAllByGenre()
                .stream()
                .map(b -> String.format("Book title: %s\n" +
                        "*Book author: %s\n" +
                        "**Date borrowed: %s\n" +
                        "***Borrowed by: %s %s\n",
                        b.getBook().getTitle()
                                , b.getBook().getAuthor()
                                ,b.getBorrowDate()
                                ,b.getLibraryMember().getFirstName()
                                ,b.getLibraryMember().getLastName()))
                .collect(Collectors.joining());
    }
}
