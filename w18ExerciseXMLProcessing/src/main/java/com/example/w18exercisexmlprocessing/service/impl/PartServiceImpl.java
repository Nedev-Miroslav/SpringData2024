package com.example.w18exercisexmlprocessing.service.impl;

import com.example.w18exercisexmlprocessing.data.entities.Part;
import com.example.w18exercisexmlprocessing.data.entities.Supplier;
import com.example.w18exercisexmlprocessing.data.repositories.PartRepository;
import com.example.w18exercisexmlprocessing.data.repositories.SupplierRepository;
import com.example.w18exercisexmlprocessing.service.PartService;
import com.example.w18exercisexmlprocessing.service.dto.imports.PartSeedDto;
import com.example.w18exercisexmlprocessing.service.dto.imports.PartSeedRootDto;
import com.example.w18exercisexmlprocessing.util.ValidationUtil;
import com.example.w18exercisexmlprocessing.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String FILE_IMPORT_PATH = "src\\main\\resources\\xml\\imports\\parts.xml";
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedParts() throws JAXBException {
        if(this.partRepository.count() == 0) {

            PartSeedRootDto partSeedRootDto = this.xmlParser.parse(PartSeedRootDto.class, FILE_IMPORT_PATH);
            for (PartSeedDto partSeedDto : partSeedRootDto.getPartSeedDtoList()) {
                if(!this.validationUtil.isValid(partSeedDto)){
                    System.out.println("Invalid data");

                    continue;
                }

                Part part = this.modelMapper.map(partSeedDto, Part.class);
                part.setSupplier(getRandomSupplier());

                this.partRepository.saveAndFlush(part);

            }


        }
    }

    private Supplier getRandomSupplier() {
        return this.supplierRepository
                .findById(
                        ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1)
                ).get();
    }
}
