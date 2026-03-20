/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Patricio
 */
public class FileOpener {

    private static final Logger LOGGER = createLogger();

    private String path;
    private String[] extensions;
    private final JFileChooser fileChooser;
    private FileNameExtensionFilter[] filters;
    private File file;
    private BufferedReader reader;

    public FileOpener() {
        fileChooser = new JFileChooser();
    }

    public boolean open(String extensionList, String approvalButtonText, String defaultDirectoryPath) {
        if (extensionList != null) {
            String[] splitExtensions = extensionList.split(",");
            trimExtensions(splitExtensions);
            setExtensions(splitExtensions);
        }

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(new File(defaultDirectoryPath));
        int selection = fileChooser.showDialog(null, approvalButtonText);
        try {
            if (selection != JFileChooser.APPROVE_OPTION) {
                return false;
            }

            file = fileChooser.getSelectedFile();
            path = file.getPath();
            reader = new BufferedReader(new FileReader(file));
            return true;
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Could not open the selected file.", exception);
            return false;
        }
    }

    public String getPath() {
        return path;
    }

    public String getFileAsString() {
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuilder fileContent = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                fileContent.append(line).append("\n");
                line = reader.readLine();
            }

            if (fileContent.length() == 0) {
                return null;
            }
            return fileContent.toString();
        } catch (FileNotFoundException exception) {
            LOGGER.log(Level.SEVERE, "File not found.", exception);
            return null;
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Could not read the selected file.", exception);
            return null;
        }
    }

    private void setExtensions(String[] extensions) {
        this.extensions = extensions;
        buildFilters();
    }

    private void buildFilters() {
        filters = new FileNameExtensionFilter[extensions.length];
        for (int i = 0; i < filters.length; i++) {
            filters[i] = new FileNameExtensionFilter("*." + extensions[i], extensions[i]);
        }
        addFilters();
    }

    private void addFilters() {
        for (FileNameExtensionFilter filter : filters) {
            fileChooser.addChoosableFileFilter(filter);
        }
    }

    private void trimExtensions(String[] extensions) {
        for (int i = 0; i < extensions.length; i++) {
            extensions[i] = extensions[i].trim();
        }
    }

    private static Logger createLogger() {
        String loggerName = FileOpener.class.getName();
        return Logger.getLogger(loggerName);
    }
}
