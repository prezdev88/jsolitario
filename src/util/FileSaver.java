/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Patricioz
 */
public class FileSaver {

    private String path;
    private String[] extensions;
    private final JFileChooser fileChooser;
    private FileNameExtensionFilter[] filters;
    private File file;

    public FileSaver() {
        fileChooser = new JFileChooser();
    }

    public boolean saveAs(
        String fileName,
        String extensionList,
        String approvalButtonText,
        String defaultDirectoryPath
    ) {
        if (extensionList != null) {
            String[] splitExtensions = extensionList.split(",");
            trimExtensions(splitExtensions);
            setExtensions(splitExtensions);
        }

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setSelectedFile(new File(fileName));
        fileChooser.setCurrentDirectory(new File(defaultDirectoryPath));
        return save(approvalButtonText);
    }

    public String getPath() {
        return path;
    }

    private boolean save(String approvalButtonText) {
        int selection = fileChooser.showDialog(null, approvalButtonText);
        try {
            if (selection != JFileChooser.APPROVE_OPTION) {
                return false;
            }

            file = fileChooser.getSelectedFile();
            path = file.getPath() + fileChooser.getFileFilter().getDescription().substring(1);
            File destinationFile = new File(path);
            if (destinationFile.exists()) {
                DialogMessages.showWarning("File Already Exists", "The file already exists. Change the file name.");
                return save(approvalButtonText);
            }
            return true;
        } catch (Exception exception) {
            return false;
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
}
