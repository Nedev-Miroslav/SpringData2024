package com.example.w18exercisexmlprocessing.service.impl;

import com.example.w18exercisexmlprocessing.data.entities.Supplier;
import com.example.w18exercisexmlprocessing.data.repositories.SupplierRepository;
import com.example.w18exercisexmlprocessing.service.SupplierService;
import com.example.w18exercisexmlprocessing.service.dto.exports.SupplierLocalDto;
import com.example.w18exercisexmlprocessing.service.dto.exports.SupplierLocalRootDto;
import com.example.w18exercisexmlprocessing.service.dto.imports.SupplierSeedDto;
import com.example.w18exercisexmlprocessing.service.dto.imports.SupplierSeedRootDto;
import com.example.w18exercisexmlprocessing.util.ValidationUtil;
import com.example.w18exercisexmlprocessing.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String FILE_IMPORT_PATH = "src\\main\\resources\\xml\\imports\\suppliers.xml";
    private static final String FILE_EXPORT_LOCAL_PATH = "src\\main\\resources\\xml\\exports\\local-supplier.xml";
    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSupplier() throws JAXBException {

        if(this.supplierRepository.count() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDto.class, FILE_IMPORT_PATH);
            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSupplierSeedDtoList()) {
                if(!this.validationUtil.isValid(supplierSeedDto)){
                    this.validationUtil.getViolations(supplierSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    System.out.println("Invalid supplier data");

                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);

            }


        }

    }
    @Override
    public void exportLocalSuppliers() throws JAXBException {
        List<SupplierLocalDto> supplierLocalDtos = this.supplierRepository.findAllByIsImporter(false)
                .stream()
                .map(s -> {
                    SupplierLocalDto dto = this.modelMapper.map(s, SupplierLocalDto.class);
                    dto.setPartsCount(s.getParts().size());
                    return dto;
                })
                .collect(Collectors.toList());

        SupplierLocalRootDto supplierLocalRootDto = new SupplierLocalRootDto();
        supplierLocalRootDto.setSupplierLocalDto(supplierLocalDtos);

        this.xmlParser.exportToFile(SupplierLocalRootDto.class, supplierLocalRootDto, FILE_EXPORT_LOCAL_PATH);
    }
}