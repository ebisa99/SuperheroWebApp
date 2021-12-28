/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ebisa
 */
@Repository
public class ImageDaoImpl implements ImageDao {

    //projects static folder.
    private final String RESOURCE_ROOT
            = "C:\\Users\\ebisa\\SGRepos\\SuperHeroSightings\\SuperheroSightings\\src\\main\\resources\\static\\";
    //This is the directory within the static folder that you want to create to 
    private final String UPDLOAD_DIRECTORY = "images\\uploads\\";

    @Override
    public String saveImage(MultipartFile file, String fileName, String directory) {
        String savedFileName = "";
        //Make sure file is a photo
        String mimetype = file.getContentType();
        if (mimetype != null && mimetype.split("/")[0].equals("image")) {
            // normalize the file path
            String originalFileName = file.getOriginalFilename();
            String[] parts = originalFileName.split("[.]");
            fileName = fileName + "." + parts[parts.length - 1];

            // save the file on the local file system
            try {
                //Full Path to the directory to be saved to. 
                String fullPath = RESOURCE_ROOT + UPDLOAD_DIRECTORY + directory + "\\";
                //Check to see if directory exists
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Path path = Paths.get(fullPath + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                savedFileName = UPDLOAD_DIRECTORY + directory + "\\" + fileName;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return savedFileName;
    }

    @Override
    public String updateImage(MultipartFile file, String fileName, String directory) {
        String savedFileName = "";

        //Deleting Old File
        //You don't delete unless there is a new file!
        if (fileName != null && !fileName.isEmpty()) {
            //Filename does exist, delete the old file
            File oldFile = new File(RESOURCE_ROOT + fileName);
            oldFile.delete();

            //Strip away the rest of the path to only get the raw filename without extension
            String[] fileNameParts = fileName.split("[\\]");
            fileName = fileNameParts[fileNameParts.length - 1].split("[.]")[0];
        } else {
            //No existing file, must come up with a filename.
            fileName = Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        }

        //Make sure file is a photo
        String mimetype = file.getContentType();
        if (mimetype != null && mimetype.split("/")[0].equals("image")) {
            // normalize the file path
            // in an update, the filename will have the save path included
            // parse the original file name just to get the file extension
            String originalFileName = file.getOriginalFilename();
            String[] originalParts = originalFileName.split("[.]");
            String fileExtension = originalParts[originalParts.length - 1];

            fileName = fileName + "." + fileExtension;

            // save the file on the local file system
            try {
                String fullPath = RESOURCE_ROOT + UPDLOAD_DIRECTORY + directory + "\\";
                //Check to see if directory exists
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Path path = Paths.get(fullPath + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                savedFileName = UPDLOAD_DIRECTORY + directory + "\\" + fileName;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return savedFileName;
    }

    @Override
    public boolean deleteImage(String oldFileName) {

        File oldFile = new File(RESOURCE_ROOT + oldFileName);
        return oldFile.delete();
    }

}
