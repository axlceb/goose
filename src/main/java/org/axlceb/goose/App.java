package org.axlceb.goose;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class App {
    public static void main(String[] args) {

        log.info("App Starts");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            // Load game config from file
            Game game = mapper.readValue(new File((new App().getClass().getClassLoader()).getResource("goose.yml").getFile()), GameGoose.class);
            game.run();
        } catch (JsonParseException e) {
            log.error("Error Parsing the Config files", e);
        } catch (JsonMappingException e) {
            log.error("Error Mapping the Config files", e);
        } catch (IOException e) {
            log.error("Error Reading the Config files", e);
        } catch (NullPointerException e) {
            log.error("Not such Config files", e);
        } catch (Exception e) {
            log.error("Unexpected error", e);
        }

        log.info("App Ends");
    }
}
