package ca.ulaval.glo4002.crmInterface.application.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.net.URL;

public class HttpRequester {

    private static ObjectMapper mapper = new ObjectMapper();

    public <T> T getDtoFromUrl(String urlToGet, Class<T> cls) throws FileNotFoundException {
        try {
            URL url = new URL(urlToGet);
            return mapper.readValue(url, cls);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (Exception e) {
            return null;
        }
    }
}

