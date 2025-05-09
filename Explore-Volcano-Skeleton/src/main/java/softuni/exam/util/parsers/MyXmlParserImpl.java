package softuni.exam.util.parsers;


import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;


@Component
public class MyXmlParserImpl implements MyXmlParser {
    @Override
    public <E> E parse(Class<E> clazz, String path) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(clazz);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        @SuppressWarnings("unchecked") // тази анотация я използваме за да спрем предупрежденията на колпилатора
        E object = (E) unmarshaller.unmarshal(new File(path));

        return object;
    }

    @Override
    public <E> void exportToFile(Class<E> clazz, E object, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(object, new File(path));

    }

}
